package com.test.weather.presentation.ui.parts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.test.domain.entity.LocationEntity
import com.test.domain.entity.WeatherEntity
import com.test.ui_global.presentation.styles.WeatherTextStyles
import com.test.weather.R
import com.test.weather.presentation.states.AddressState
import com.test.weather.presentation.viewModel.MainViewModel

@Composable
fun CurrentAddress(mainViewModel: MainViewModel = viewModel()) {
    val addressState = mainViewModel.addressState.collectAsState(AddressState.Loading)

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        when (val currentState = addressState.value) {
            is AddressState.Success -> AddressContent(currentState.location)

            AddressState.Loading -> ShowAddressLoading()

            is AddressState.Error -> {
                //TODO segmented error handling }
            }
        }
    }
}

@Composable
fun AddressContent(location: LocationEntity) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(stringResource(R.string.current_address))
        Text(
            textAlign = TextAlign.Center,
            style = WeatherTextStyles.WeatherMediumText,
            text = location.name
        )
    }
}

@Composable
fun ShowAddressLoading() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAddressWeather() {
    Column(Modifier.fillMaxSize()) {
        AddressContent(
            location = LocationEntity(
                name = "ipsum?", lat = 111.1, lon = 222.2
            )
        )
        //ShowAddressLoading()
        Column(modifier = Modifier.fillMaxHeight(0.3f)) {
            WeatherContent(WeatherEntity())
        }
    }
}
