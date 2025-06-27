package com.test.domain.usecase

import com.test.domain.repository.WeatherRepository
import javax.inject.Inject

class FetchAddressUseCase @Inject constructor(
    private val repository: WeatherRepository
) {
    suspend operator fun invoke(lat: Double, lon: Double) = repository.fetchAddress(lat, lon)
}