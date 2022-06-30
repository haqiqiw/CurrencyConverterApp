package com.example.currencyconverterapp.data.repository

import com.example.currencyconverterapp.data.remote.model.RatesResponse
import com.example.currencyconverterapp.util.Resource

interface RatesRepository {

    suspend fun getRates(base: String): Resource<RatesResponse>
}
