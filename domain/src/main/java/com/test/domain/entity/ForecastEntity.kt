package com.test.domain.entity

data class ForecastEntity(
    val dailyForecasts: List<DayEntity>
)

data class DayEntity(
    val date: String,
    val weatherCode: Int,
    val meanTemperature: Double,
    val weatherDescription: Int
)
