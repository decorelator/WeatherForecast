package com.test.forecastfeture

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.test.domain.entity.DayEntity
import com.test.domain.entity.ForecastEntity
import com.test.forecastfeture.viewModel.ForecastState
import com.test.forecastfeture.viewModel.ForecastViewModel
import com.test.ui_global.presentation.ShowLoading
import com.test.ui_global.presentation.styles.WeatherTextStyles


@Composable
fun WeatherForecast(
    forecastViewModel: ForecastViewModel = viewModel()
) {
    val forecastState = forecastViewModel.forecastState.collectAsState(ForecastState.Loading)

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        when (val currentState = forecastState.value) {
            ForecastState.Loading -> ShowLoading()

            is ForecastState.Success -> ForecastContent(currentState.forecast)

            is ForecastState.Error -> {}

        }
    }
}

@Composable
fun ForecastContent(forecast: ForecastEntity) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(18.dp),
        textAlign = TextAlign.Center,
        style = WeatherTextStyles.WeatherMediumText,
        text = stringResource(R.string.forecast)
    )

    Divider()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 4.dp)
    ) {
        itemsIndexed(
            items = forecast.dailyForecasts,
            key = { _, item -> item.date }) { index, dailyForecast ->
            DayRow(dayEntity = dailyForecast)

            if (index < forecast.dailyForecasts.lastIndex) {
                Divider()
            }
        }
    }
}

@Composable
private fun Divider() {
    HorizontalDivider(
        modifier = Modifier.padding(horizontal = 8.dp),
        thickness = 1.dp,
        color = MaterialTheme.colorScheme.secondary
    )
}

@Composable
fun DayRow(dayEntity: DayEntity) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            RowText(dayEntity.date)
            Text("  avg temp:")
            RowText(text = dayEntity.meanTemperature.toString(), 6.dp)
        }
        Row (modifier = Modifier.padding(top = 4.dp)){
            Text(stringResource(R.string.condition))
            RowText(stringResource(id = dayEntity.weatherDescription))
        }
    }
}

@Composable
fun RowText(text: String, horizontalPadding: Dp = 4.dp) {
    Text(
        modifier = Modifier.padding(horizontal = horizontalPadding),
        style = WeatherTextStyles.WeatherMediumText,
        text = text
    )
}

@Preview(showBackground = true, apiLevel = 33)
@Composable
fun PreviewForecast() {
    Column(Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxHeight(0.3f)) {
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.DarkGray)
        ) {
            ForecastContent(
                ForecastEntity(
                    dailyForecasts = listOf<DayEntity>(
                        DayEntity("date", 10, 0.0, R.string.weather_fog),
                        DayEntity("date4", 11, 1.0, R.string.weather_rime_fog),
                        DayEntity("date3", 12, 2.0, R.string.weather_heavy_snow_fall),
                        DayEntity("date2", 13, 5.0, R.string.weather_fog),
                        DayEntity("date1", 18, 3.0, R.string.weather_fog),
                    )
                )
            )
        }
    }
}
