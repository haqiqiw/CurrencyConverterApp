package com.example.currencyconverterapp.data.remote.service

import com.example.currencyconverterapp.data.remote.model.ConvertResponse
import com.example.currencyconverterapp.data.remote.model.RatesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyService {

    @GET("/latest")
    suspend fun getRates(
        @Query("base") from: String
    ): Response<RatesResponse>

    @GET("/convert")
    suspend fun convertCurrency(
        @Query("amount") amount: Double,
        @Query("from") from: String,
        @Query("to") to: String
    ): Response<ConvertResponse>
}
