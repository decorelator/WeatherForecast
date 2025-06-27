package com.test.weather.presentation.states

sealed class UIEvent {
    data class ShowSnackBar(val message: String) : UIEvent()
}