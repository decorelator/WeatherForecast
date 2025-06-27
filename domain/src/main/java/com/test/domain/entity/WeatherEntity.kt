package com.test.domain.entity

import java.util.Date

data class WeatherEntity(
    val date: Date = Date(),
    val temperature: Float = 0F,
    val temperatureFeelsLike: Float = 0F,
    val condition: String = "",
    val iconUrl: String = ""
)

