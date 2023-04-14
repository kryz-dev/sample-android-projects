package com.example.airportapp.network

import com.example.airportapp.model.Airport
import io.reactivex.rxjava3.core.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiService {
    @GET("flight/refData/airport")
    fun getAirportList(): Observable<List<Airport>>
}

object AirportNetwork {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.qantas.com/")
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val getAirportService = retrofit.create(ApiService::class.java)
}