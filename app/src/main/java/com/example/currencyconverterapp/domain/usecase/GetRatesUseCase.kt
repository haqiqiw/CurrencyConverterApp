package com.example.currencyconverterapp.domain.usecase

import com.example.currencyconverterapp.domain.model.Currency

interface GetRatesUseCase {

    suspend fun getRates(base: String): List<Currency>
}
