package com.test.data.mappers

import com.test.data.dto.AddressDto
import com.test.data.dto.LocationDto
import com.test.domain.entity.LocationEntity

fun LocationDto.toLocationEntity(): LocationEntity {
    val parsedLat = this.lat.toDoubleOrNull() ?: 0.0
    val parsedLon = this.lon.toDoubleOrNull() ?: 0.0

    val combinedName = "${this.name}, ${this.country}"

    return LocationEntity(
        name = combinedName,
        lat = parsedLat,
        lon = parsedLon
    )
}

fun List<LocationDto>.toLocationEntityList(): List<LocationEntity> {
    return this.map { it.toLocationEntity() }
}

fun AddressDto.toLocationEntity(): LocationEntity {
    return LocationEntity(
        name = "$name, $country",
        lat = lat,
        lon = lon
    )
}

fun List<AddressDto>.toLocationEntityListFromAddress(): List<LocationEntity> {
    return this.map { it.toLocationEntity() }
}