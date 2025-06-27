package com.test.data.di

import com.test.data.repositoryImpl.ForecastRepositoryImpl
import com.test.data.repositoryImpl.WeatherRepositoryImpl
import com.test.domain.repository.ForecastRepository
import com.test.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindWeatherRepository(
        weatherRepositoryImpl: WeatherRepositoryImpl
    ): WeatherRepository

    @Binds
    @Singleton
    abstract fun forecastRepository(
        forecastRepositoryImpl: ForecastRepositoryImpl
    ): ForecastRepository
}