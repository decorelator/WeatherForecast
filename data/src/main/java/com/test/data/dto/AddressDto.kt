package com.test.data.dto

import com.google.gson.annotations.SerializedName

data class AddressDto(
    @SerializedName("name") val name: String,
    @SerializedName("lat") val lat: Double,
    @SerializedName("lon") val lon: Double,
    @SerializedName("country") val country: String, // Country code (e.g., "GB")
)
