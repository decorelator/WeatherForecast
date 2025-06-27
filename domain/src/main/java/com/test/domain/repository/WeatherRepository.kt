package com.test.domain.repository

import com.test.domain.entity.LocationEntity
import com.test.domain.entity.WeatherEntity
import kotlinx.coroutines.flow.StateFlow

interface WeatherRepository {
    val currentLocation: StateFlow<Result<LocationEntity>?>

    suspend fun getCurrentWeather(place: LocationEntity): Result<WeatherEntity> {
        throw IllegalStateException("getCurrentWeather() must be implemented")
    }

    suspend fun getLocation(address: String) {
        throw IllegalStateException("getLocation() must be implemented")
    }

    suspend fun fetchAddress(lat: Double, lon: Double) {
        throw IllegalStateException("fetchAddress() must be implemented")
    }
}