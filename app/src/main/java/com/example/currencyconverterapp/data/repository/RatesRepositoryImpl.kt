package com.example.currencyconverterapp.data.repository

import com.example.currencyconverterapp.data.remote.model.RatesResponse
import com.example.currencyconverterapp.data.remote.service.CurrencyService
import com.example.currencyconverterapp.util.Resource
import javax.inject.Inject

class RatesRepositoryImpl @Inject constructor(
    private val currencyService: CurrencyService
) : RatesRepository {

    override suspend fun getRates(base: String): Resource<RatesResponse> {
        return try {
            val response = currencyService.getRates(base)
            val result = response.body()
            if (response.isSuccessful && result != null) {
                Resource.Success(result)
            } else {
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occured")
        }
    }
}
