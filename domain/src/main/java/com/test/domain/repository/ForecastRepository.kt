package com.test.domain.repository

import com.test.domain.entity.ForecastEntity


interface ForecastRepository {

    suspend fun getForecastWeather(lat: Double, lon: Double): Result<ForecastEntity> {
        throw IllegalStateException("getForecastWeather() must be implemented")
    }
}