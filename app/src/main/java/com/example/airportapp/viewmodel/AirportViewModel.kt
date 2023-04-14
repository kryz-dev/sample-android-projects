package com.example.airportapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.airportapp.model.Airport
import java.util.*

class AirportViewModel : ViewModel() {
    private val _isLoading = MutableLiveData(true)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _airportList = MutableLiveData<List<Airport>>()
    val airportList: LiveData<List<Airport>> = _airportList

    private val _selectedAirport = MutableLiveData<Airport>()
    val selectedAirport: LiveData<Airport> = _selectedAirport

    private val _selectedCurrency = MutableLiveData<Currency>()
    val selectedCurrency: LiveData<Currency> = _selectedCurrency

    fun setSelectedAirport(selectedAirport: Airport){
        _selectedAirport.value = selectedAirport
    }

    fun setSelectedCurrency(currency: Currency){
        _selectedCurrency.value = currency
    }

    fun showLoading() {
        _isLoading.value = true
    }

    fun hideLoading() {
        _isLoading.value = false
    }
}