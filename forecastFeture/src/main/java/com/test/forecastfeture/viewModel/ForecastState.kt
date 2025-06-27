package com.test.forecastfeture.viewModel

import com.test.domain.entity.ForecastEntity

sealed class ForecastState {
    data object Loading : ForecastState()
    data class Success(val forecast: ForecastEntity) : ForecastState()
    data class Error(val forecast: String) : ForecastState()
}
