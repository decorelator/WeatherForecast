package com.test.data.di

import javax.inject.Qualifier

class Qualifiers {
    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class OpenWeatherRetrofit

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class OpenMeteoRetrofit
}