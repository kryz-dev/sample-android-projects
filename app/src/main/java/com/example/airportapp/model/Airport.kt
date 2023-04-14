package com.example.airportapp.model

import com.example.airportapp.database.AirportEntity
import com.google.gson.annotations.SerializedName

data class Airport(
    @SerializedName("airportName")
    val airportName: String,
    @SerializedName("location")
    val location: Location,
    @SerializedName("country")
    val country: Country,
    @SerializedName("city")
    val city: City
)

data class Location(
    @SerializedName("latitude")
    val latitude: Double,
    @SerializedName("longitude")
    val longitude: Double
)

data class Country(
    @SerializedName("countryName")
    val countryName: String,
    @SerializedName("countryCode")
    val countryCode: String
)

data class City(
    @SerializedName("cityCode")
    val cityCode: String,
    @SerializedName("cityName")
    val cityName: String?,
    @SerializedName("timeZoneName")
    val timeZoneName: String
)

fun List<Airport>.toEntity(): List<AirportEntity> {
    return map {
        AirportEntity(
            airportName = it.airportName,
            countryCode = it.country.countryCode,
            countryName = it.country.countryName,
            cityCode = it.city.cityCode,
            cityName = it.city.cityName,
            latitude = it.location.latitude,
            longitude = it.location.longitude,
            timezoneName = it.city.timeZoneName
        )
    }
}
