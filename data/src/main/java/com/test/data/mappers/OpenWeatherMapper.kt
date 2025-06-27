package com.test.data.mappers

import com.test.data.dto.OpenWeatherResponseDto
import com.test.domain.entity.WeatherEntity
import java.util.Date
import java.util.concurrent.TimeUnit

fun OpenWeatherResponseDto.toWeatherEntity(): WeatherEntity {
    val date = Date(TimeUnit.SECONDS.toMillis(this.dt))

    val firstWeather = this.weather.firstOrNull()
    val condition = firstWeather?.description ?: ""
    val iconCode = firstWeather?.icon ?: ""
    val iconUrl = if (iconCode.isNotEmpty()) {
        "https://openweathermap.org/img/wn/$iconCode@4x.png"
    } else {
        ""
    }

    return WeatherEntity(
        date = date,
        temperature = this.main.temp,
        temperatureFeelsLike = this.main.feelsLike,
        condition = condition,
        iconUrl = iconUrl
    )
}