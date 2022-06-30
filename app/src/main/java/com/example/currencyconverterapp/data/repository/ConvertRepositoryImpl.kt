package com.example.currencyconverterapp.data.repository

import com.example.currencyconverterapp.data.remote.model.ConvertResponse
import com.example.currencyconverterapp.data.remote.service.CurrencyService
import com.example.currencyconverterapp.util.Resource
import javax.inject.Inject

class ConvertRepositoryImpl @Inject constructor(
    private val currencyService: CurrencyService
) : ConvertRepository {

    override suspend fun convertCurrency(
        amount: Double,
        from: String,
        to: String
    ): Resource<ConvertResponse> {
        return try {
            val response = currencyService.convertCurrency(amount, from, to)
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
