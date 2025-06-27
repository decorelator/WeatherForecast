package com.test.data.repositoryImpl

import com.test.data.mappers.toForecastEntity
import com.test.data.network.OpenMeteoApi
import com.test.domain.entity.ForecastEntity
import com.test.domain.repository.ForecastRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ForecastRepositoryImpl @Inject constructor(
    private val openMeteoApiRequests: OpenMeteoApi
) : ForecastRepository {

    override suspend fun getForecastWeather(lat: Double, lon: Double): Result<ForecastEntity> {
        return try {
            val forecastResponse =
                openMeteoApiRequests.getForecast(lat, lon, daysCount = 7)
            Result.success(forecastResponse.toForecastEntity())

        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
