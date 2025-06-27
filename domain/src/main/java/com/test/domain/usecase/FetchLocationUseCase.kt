package com.test.domain.usecase

import com.test.domain.repository.WeatherRepository
import javax.inject.Inject

class FetchLocationUseCase @Inject constructor(
    private val repository: WeatherRepository
) {
    suspend operator fun invoke(address: String) = repository.getLocation(address)
}