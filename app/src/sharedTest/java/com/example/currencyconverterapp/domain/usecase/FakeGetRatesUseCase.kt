package com.example.currencyconverterapp.domain.usecase

import com.example.currencyconverterapp.domain.model.Currency
import javax.inject.Inject

class FakeGetRatesUseCase @Inject constructor() : GetRatesUseCase {

    var getRatesValue: List<Currency> = listOf(Currency("IDR", 1000.0), Currency("SGD", 100.0))
    override suspend fun getRates(base: String): List<Currency> {
        return getRatesValue
    }
}
