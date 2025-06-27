package com.test.data.mappers

import com.test.data.dto.OpenMeteoResponseDto
import com.test.domain.entity.DayEntity
import com.test.domain.entity.ForecastEntity


fun OpenMeteoResponseDto.toForecastEntity(): ForecastEntity {
    val dailyRepresentations = mutableListOf<DayEntity>()

    for (i in this.daily.time.indices) {
        val date = this.daily.time[i] ?: continue
        val weatherCodeInt = this.daily.weatherCode[i] ?: continue
        val meanTemperature = this.daily.temperature2mMean[i] ?: continue

        val weatherCondition = WeatherCondition.fromCode(weatherCodeInt)

        dailyRepresentations.add(
            DayEntity(
                date = date,
                weatherCode = weatherCodeInt,
                weatherDescription = weatherCondition.descriptionResId,
                meanTemperature = meanTemperature
            )
        )
    }
    return ForecastEntity(dailyRepresentations)
}