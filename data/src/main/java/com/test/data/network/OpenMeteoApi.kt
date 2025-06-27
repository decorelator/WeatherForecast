package com.test.data.network


import com.test.data.dto.OpenMeteoResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenMeteoApi : BaseRequest {

    enum class Units(val value: String) {
        METRIC("celsius"),
        IMPERIAL("fahrenheit")
    }

    //forecast_days=16
    @GET("v1/forecast?daily=weathercode,temperature_2m_mean&timezone=auto&weather_code=wmo")
    suspend fun getForecast(
        @Query("latitude") lat: Double,
        @Query("longitude") lon: Double,
        @Query("temperature_unit") units: String = Units.METRIC.value,
        @Query("forecast_days") daysCount: Int = 7,
    ): OpenMeteoResponseDto

}