package com.test.weather.presentation.states

import com.test.domain.entity.LocationEntity

sealed class AddressState {
    data object Loading : AddressState()
    data class Success(val location: LocationEntity) : AddressState()
    data class Error(val message: String) : AddressState()
}
