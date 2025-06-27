package com.test.domain.usecase

import com.test.domain.repository.ForecastRepository
import javax.inject.Inject

class GetForecastUseCase @Inject constructor(
    private val repository: ForecastRepository
) {
    suspend operator fun invoke(lat: Double, lon: Double) = repository.getForecastWeather(lat, lon)
}