package com.test.domain.usecase

import com.test.domain.entity.LocationEntity
import com.test.domain.repository.WeatherRepository
import javax.inject.Inject

class FetchWeatherUseCase @Inject constructor(
    private val repository: WeatherRepository
) {
    suspend operator fun invoke(location: LocationEntity) = repository.getCurrentWeather(location)
}