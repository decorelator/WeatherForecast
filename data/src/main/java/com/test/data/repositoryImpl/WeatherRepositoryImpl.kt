package com.test.data.repositoryImpl

import com.test.data.mappers.toLocationEntityList
import com.test.data.mappers.toLocationEntityListFromAddress
import com.test.data.mappers.toWeatherEntity
import com.test.data.network.OpenWeatherApi
import com.test.domain.entity.LocationEntity
import com.test.domain.entity.WeatherEntity
import com.test.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepositoryImpl @Inject constructor(
    private val openWeatherApiRequests: OpenWeatherApi
) : WeatherRepository {
    companion object {
        const val OPEN_WEATHER_API_KEY = "ae9c45a9ca652b8b03d0f6d6bd039908"
    }

    private val _currentLocation = MutableStateFlow<Result<LocationEntity>?>(null)

    override val currentLocation: StateFlow<Result<LocationEntity>?> =
        _currentLocation.asStateFlow()

    override suspend fun getCurrentWeather(place: LocationEntity): Result<WeatherEntity> {
        return try {
            val currentWeatherResponse =
                openWeatherApiRequests.getCurrentWeather(
                    place.lat,
                    place.lon,
                    appId = OPEN_WEATHER_API_KEY
                )
            Result.success(currentWeatherResponse.toWeatherEntity())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getLocation(address: String) {
        try {
            val locationResponse =
                openWeatherApiRequests.getLatLon(address = address, appId = OPEN_WEATHER_API_KEY)
            _currentLocation.value = Result.success(locationResponse.toLocationEntityList()[0])
        } catch (e: Exception) {
            _currentLocation.value = Result.failure(e)
        }
    }

    override suspend fun fetchAddress(lat: Double, lon: Double) {
        try {
            val locationResponse = openWeatherApiRequests.getCity(
                lat = lat,
                lon = lon,
                appId = OPEN_WEATHER_API_KEY
            )
            _currentLocation.value =
                Result.success(locationResponse.toLocationEntityListFromAddress()[0])
        } catch (e: Exception) {
            _currentLocation.value = Result.failure(e)
        }
    }

}
