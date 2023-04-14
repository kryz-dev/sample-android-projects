package com.example.airportapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.airportapp.model.Airport
import com.example.airportapp.model.City
import com.example.airportapp.model.Country
import com.example.airportapp.model.Location

@Entity
data class AirportEntity constructor(
    @PrimaryKey
    val airportName: String,
    val countryCode: String,
    val countryName: String,
    val cityCode: String,
    val cityName: String?="",
    val latitude: Double,
    val longitude: Double,
    val timezoneName: String
)

fun List<AirportEntity>.toModel(): List<Airport> {
    return map {
        Airport(
            airportName = it.airportName,
            location = Location(it.latitude, it.longitude),
            country = Country(it.countryName, it.countryCode),
            city = City(it.cityCode,it.cityName,it.timezoneName)
        )
    }
}