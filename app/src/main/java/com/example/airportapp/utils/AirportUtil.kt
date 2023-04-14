package com.example.airportapp.utils

import java.util.*

object AirportUtil {
    fun getCurrencyByCountryCode(countryCode: String) : Currency{
        return Currency.getInstance(Locale("", countryCode))
    }
}