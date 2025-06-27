package com.test.weather.presentation.states

import com.test.domain.entity.WeatherEntity

sealed class WeatherState {
    data object Loading : WeatherState()
    data class Success(val weather: WeatherEntity) : WeatherState()
    data class Error(val message: String) : WeatherState()
}