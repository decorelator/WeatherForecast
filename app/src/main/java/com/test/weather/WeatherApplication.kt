package com.test.weather


import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class WeatherApplication : Application() {
    // This class is needed for Hilt to generate code
}