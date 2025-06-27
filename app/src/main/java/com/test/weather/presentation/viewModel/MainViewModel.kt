package com.test.weather.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.domain.entity.LocationEntity
import com.test.domain.usecase.FetchAddressUseCase
import com.test.domain.usecase.GetCurrentLocationUseCase
import com.test.domain.usecase.FetchLocationUseCase
import com.test.domain.usecase.FetchWeatherUseCase
import com.test.weather.presentation.states.AddressState
import com.test.weather.presentation.states.UIEvent
import com.test.weather.presentation.states.WeatherState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val fetchAddressUseCase: FetchAddressUseCase,
    private val getCurrentLocationUseCase: GetCurrentLocationUseCase,
    private val fetchLocationUseCase: FetchLocationUseCase,
    private val fetchWeatherUseCase: FetchWeatherUseCase
) : ViewModel() {

    private val initialWeatherState = WeatherState.Loading
    private val _weatherState = MutableStateFlow<WeatherState>(initialWeatherState)
    val weatherState: StateFlow<WeatherState> = _weatherState

    private val initialAddress = AddressState.Loading
    private val _addressState = MutableStateFlow<AddressState>(initialAddress)
    val addressState: StateFlow<AddressState> = _addressState

    private val _uiEvent = MutableSharedFlow<UIEvent>()
    val uiEvent: SharedFlow<UIEvent> = _uiEvent

    init {
        viewModelScope.launch {
            getCurrentLocationUseCase().collect { location ->
                if (location != null) {
                    location.fold(
                        onSuccess = { successLocation ->
                            loadCurrentWeather(successLocation)
                            _addressState.value = AddressState.Success(successLocation)
                        },
                        onFailure = { _uiEvent.emit(UIEvent.ShowSnackBar("address not found")) }
                    )
                } else {
                    _uiEvent.emit(UIEvent.ShowSnackBar("address not found"))
                }
            }
        }
    }

    fun loadCity(
        address: String
    ) {
        viewModelScope.launch {
            fetchLocationUseCase(address)
        }
    }

    fun loadAddress(
        lat: Double,
        lon: Double,
    ) {
        viewModelScope.launch {
            fetchAddressUseCase(lat, lon)
        }
    }

    suspend fun showSnackBarMessage(message: String) {
        _uiEvent.emit(UIEvent.ShowSnackBar(message))
    }

    private fun loadCurrentWeather(location: LocationEntity) {
        _weatherState.value = WeatherState.Loading
        viewModelScope.launch {
            val response = fetchWeatherUseCase(location)
            response.fold(onSuccess = { weather ->
                _weatherState.value = WeatherState.Success(weather)
            }, onFailure = {
                _uiEvent.emit(UIEvent.ShowSnackBar("Failed to load current weather. Please retry."))
            })
        }
    }

}