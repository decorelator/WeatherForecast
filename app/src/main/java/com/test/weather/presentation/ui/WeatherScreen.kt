package com.test.weather.presentation.ui

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.location.LocationServices
import com.test.domain.entity.LocationEntity
import com.test.forecastfeture.PreviewForecast
import com.test.forecastfeture.WeatherForecast
import com.test.weather.presentation.states.UIEvent
import com.test.weather.presentation.ui.parts.AddressContent
import com.test.weather.presentation.ui.parts.CurrentAddress
import com.test.weather.presentation.ui.parts.CurrentWeather
import com.test.weather.presentation.ui.parts.RequestLocation
import com.test.weather.presentation.ui.parts.managedActivityResultLauncher
import com.test.weather.presentation.viewModel.MainViewModel
import com.test.weather.ui.theme.WeatherTheme

@Composable
fun WeatherScreen() {
    Content()
}

@Composable
private fun Content(mainViewModel: MainViewModel = viewModel()) {

    val snackBarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }

    val requestPermissionLauncher =
        managedActivityResultLauncher(fusedLocationClient, mainViewModel, scope)

    // Trigger location request on app start
    RequestLocation(fusedLocationClient, mainViewModel, requestPermissionLauncher)

    ListenUIState(snackBarHostState, mainViewModel)

    Scaffold(snackbarHost = { SnackbarHost(snackBarHostState) }) { padding ->
        BodyContent(padding)
    }
}

@Composable
private fun BodyContent(padding: PaddingValues) {

    Column(Modifier.padding(padding)) {
        CitySearch()
        CurrentAddress()
        CurrentWeather()
        WeatherForecast()
    }
}

@Composable
private fun ListenUIState(
    snackBarHostState: SnackbarHostState,
    mainViewModel: MainViewModel
) {
    LaunchedEffect(key1 = snackBarHostState) {
        mainViewModel.uiEvent.collect { event ->
            when (event) {
                is UIEvent.ShowSnackBar -> {
                    snackBarHostState.showSnackbar(
                        message = event.message,
                        withDismissAction = true,
                        duration = SnackbarDuration.Short
                    )
                }
            }
        }
    }
}

@Composable
fun CitySearch(mainViewModel: MainViewModel = viewModel()) {
    val outlinedText = rememberSaveable { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        value = outlinedText.value,
        onValueChange = { newText ->
            outlinedText.value = newText
        },
        label = { Text("Location") },
        placeholder = { Text("e.g., London, GB") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(onDone = {
            Log.d("EnterAction", "Text submitted: $outlinedText")
            focusManager.clearFocus()
            mainViewModel.loadCity(outlinedText.value)
        })
    )
}


@Preview(apiLevel = 33)
@Composable
fun PreviewWeather() {
    WeatherTheme {
        Column(Modifier.fillMaxSize()) {

            AddressContent(
                location = LocationEntity(
                    name = "test lorem", lat = 111.1, lon = 222.2
                )
            )
            PreviewForecast()
        }
    }
}
