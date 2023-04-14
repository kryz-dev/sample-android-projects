package com.example.airportapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.airportapp.database.AirportDatabase
import com.example.airportapp.database.toModel
import com.example.airportapp.model.Airport
import com.example.airportapp.model.toEntity
import com.example.airportapp.network.AirportNetwork
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AirportRepository(
    private val database: AirportDatabase,
    private val onSuccess: (List<Airport>) -> Unit,
    private val onFailure: (Throwable) -> Unit
) {
    val airports: LiveData<List<Airport>> = database.airportDao.getAirports().map {
        it.toModel()
    }

    fun getAirportList() {
            val observable = AirportNetwork.getAirportService.getAirportList()
            observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    onSuccess,
                    onFailure)
    }

    suspend fun saveDataToDatabase(airportList: List<Airport>){
        withContext(Dispatchers.IO) {
            database.airportDao.insertAll(airportList.toEntity())
        }
    }
}