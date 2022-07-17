package com.example.currencyconverterapp.data.repository

import com.example.currencyconverterapp.data.remote.model.Rates
import com.example.currencyconverterapp.data.remote.model.RatesResponse
import com.example.currencyconverterapp.util.Resource
import javax.inject.Inject

class FakeRatesRepository @Inject constructor() : RatesRepository {

    var getRatesResource: Resource<RatesResponse> = resourceSuccess()
    override suspend fun getRates(base: String): Resource<RatesResponse> {
        return getRatesResource
    }

    companion object {
        fun resourceSuccess(): Resource.Success<RatesResponse> {
            return Resource.Success(
                RatesResponse(
                    "USD",
                    "1-2-2022",
                    Rates(
                        1.0,
                        1.0,
                        1.0,
                        1.0,
                        1.0,
                        1.0,
                        1.0,
                        1.0,
                        1.0,
                        1.0,
                        1.0,
                        1.0,
                        1.0,
                        1.0,
                        1.0,
                        1.0,
                        1.0,
                        1.0,
                        1.0,
                        1.0,
                        1.0,
                        1.0,
                        1.0,
                        1.0,
                        1.0,
                        1.0
                    ),
                    true
                )
            )
        }

        fun resourceError(): Resource.Error {
            return Resource.Error("Resource error")
        }
    }
}
