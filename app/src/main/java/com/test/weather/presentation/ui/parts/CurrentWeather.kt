package com.test.weather.presentation.ui.parts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.test.domain.entity.WeatherEntity
import com.test.weather.R
import com.test.weather.presentation.states.WeatherState
import com.test.weather.presentation.viewModel.MainViewModel

@Composable
fun CurrentWeather(mainViewModel: MainViewModel = viewModel()) {
    val weatherState = mainViewModel.weatherState.collectAsState(WeatherState.Loading)
    Column(
        modifier = Modifier
            .fillMaxHeight(0.3f)
            .fillMaxWidth()
    ) {

        when (val currentState = weatherState.value) {
            is WeatherState.Success -> WeatherContent(currentState.weather)

            WeatherState.Loading -> ShowLoading()

            is WeatherState.Error -> ShowWeatherError()
        }
    }
}

@Composable
fun ShowWeatherError() {
    Text(text = stringResource(R.string.weather_error))
}

@Composable
fun WeatherContent(weather: WeatherEntity) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            Modifier.fillMaxWidth(0.3f),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(weather.iconUrl)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.ic_launcher_foreground),
                contentScale = ContentScale.Fit,
                contentDescription = "Image loaded from URL",
            )
        }
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Text("Current Temperature: " + weather.temperature)
            Text("feels Like:" + weather.temperatureFeelsLike)
            Text(weather.condition)
            Text(weather.date.toString())
        }
    }
}

@Composable
fun ShowLoading() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewWeather() {
    Column(Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxHeight(0.3f)) {
            WeatherContent(WeatherEntity())
        }
    }
}
