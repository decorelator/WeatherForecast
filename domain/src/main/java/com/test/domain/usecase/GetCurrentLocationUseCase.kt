package com.test.domain.usecase

import com.test.domain.repository.WeatherRepository
import javax.inject.Inject

class GetCurrentLocationUseCase @Inject constructor(
    private val repository: WeatherRepository
) {
    operator fun invoke() = repository.currentLocation
}