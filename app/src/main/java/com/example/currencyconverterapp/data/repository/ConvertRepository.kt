package com.example.currencyconverterapp.data.repository

import com.example.currencyconverterapp.data.remote.model.ConvertResponse
import com.example.currencyconverterapp.util.Resource

interface ConvertRepository {

    suspend fun convertCurrency(
        amount: Double,
        from: String,
        to: String
    ): Resource<ConvertResponse>
}
