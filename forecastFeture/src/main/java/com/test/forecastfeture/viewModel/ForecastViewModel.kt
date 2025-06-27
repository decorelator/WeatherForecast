package com.test.forecastfeture.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.domain.usecase.GetCurrentLocationUseCase
import com.test.domain.usecase.GetForecastUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForecastViewModel @Inject constructor(
    private val getForecastUseCase: GetForecastUseCase,
    private val getCurrentLocationUseCase: GetCurrentLocationUseCase,
) : ViewModel() {

    private val initialWeatherState = ForecastState.Loading
    private val _forecastState = MutableStateFlow<ForecastState>(initialWeatherState)
    val forecastState: StateFlow<ForecastState> = _forecastState

    init {
        viewModelScope.launch {
            getCurrentLocationUseCase().collect { location ->
                location?.let {
                    it.fold(onSuccess = { location ->
                        loadForecast(location.lat, location.lon)
                    }, onFailure = {})

                }
            }
        }
    }

    private fun loadForecast(lat: Double, lon: Double) {
        _forecastState.value = ForecastState.Loading
        viewModelScope.launch {
            val response = getForecastUseCase(lat, lon)
            response.fold(
                onSuccess = { forecast -> _forecastState.value = ForecastState.Success(forecast) },
                onFailure = {
                    // nice error handling
                }
            )
        }
    }
}