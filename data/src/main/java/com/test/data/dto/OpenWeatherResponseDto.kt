package com.test.data.dto

import com.google.gson.annotations.SerializedName

data class OpenWeatherResponseDto(
    @SerializedName("weather") val weather: List<OpenWeatherWeatherDto>,
    @SerializedName("main") val main: OpeOpenWeatherMain,
    @SerializedName("dt") val dt: Long
)

data class OpenWeatherWeatherDto(
    @SerializedName("description") val description: String,
    @SerializedName("icon") val icon: String
)

data class OpeOpenWeatherMain(
    @SerializedName("temp") val temp: Float,
    @SerializedName("feels_like") val feelsLike: Float,
)

