package com.test.data.dto

import com.google.gson.annotations.SerializedName

data class LocationDto(
    @SerializedName("name") val name: String,
    @SerializedName("lat") val lat: String,
    @SerializedName("lon") val lon: String,
    @SerializedName("country") val country: String
)
