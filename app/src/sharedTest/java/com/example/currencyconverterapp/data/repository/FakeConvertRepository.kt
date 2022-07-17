package com.example.currencyconverterapp.data.repository

import com.example.currencyconverterapp.data.remote.model.ConvertResponse
import com.example.currencyconverterapp.data.remote.model.Info
import com.example.currencyconverterapp.data.remote.model.Query
import com.example.currencyconverterapp.util.Resource
import javax.inject.Inject

class FakeConvertRepository @Inject constructor() : ConvertRepository {

    var convertCurrencyResource: Resource<ConvertResponse> = resourceSuccess()
    override suspend fun convertCurrency(amount: Double, from: String, to: String): Resource<ConvertResponse> {
        return convertCurrencyResource
    }

    companion object {
        fun resourceSuccess(): Resource.Success<ConvertResponse> {
            return Resource.Success(
                ConvertResponse(
                    "1-2-2022",
                    false,
                    Info(1.0),
                    Query(1, "USD", "IDR"),
                    1000.0,
                    true
                )
            )
        }

        fun resourceError(): Resource.Error {
            return Resource.Error("Resource error")
        }
    }
}
