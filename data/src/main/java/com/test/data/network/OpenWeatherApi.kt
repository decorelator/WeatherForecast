package com.test.data.network

import com.test.data.dto.AddressDto
import com.test.data.dto.LocationDto
import com.test.data.dto.OpenWeatherResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherApi : BaseRequest {

    enum class Units(val value: String) {
        METRIC("metric"), IMPERIAL("imperial")
    }

    @GET("geo/1.0/direct")
    suspend fun getLatLon(
        @Query("q") address: String,
        @Query("limit") limit: Int = 1,
        @Query("appid") appId: String,
    ): List<LocationDto>

    @GET("geo/1.0/reverse")
    suspend fun getCity(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("limit") limit: Int = 1,
        @Query("appid") appId: String,
    ): List<AddressDto>

    @GET("data/2.5/weather")
    suspend fun getCurrentWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") units: String = Units.METRIC.value,
        @Query("appid") appId: String
    ): OpenWeatherResponseDto

}