package com.test.data.dto

import com.google.gson.annotations.SerializedName

data class OpenMeteoResponseDto(

    @SerializedName("daily")
    val daily: Daily
)

data class Daily(
    @SerializedName("time")
    val time: List<String?>,
    @SerializedName("weathercode")
    val weatherCode: List<Int?>,
    @SerializedName("temperature_2m_mean")
    val temperature2mMean: List<Double?>
)