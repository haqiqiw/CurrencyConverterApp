package com.example.currencyconverterapp.domain.usecase

import com.example.currencyconverterapp.data.remote.model.Rates
import com.example.currencyconverterapp.data.repository.RatesRepository
import com.example.currencyconverterapp.domain.model.Currency
import com.example.currencyconverterapp.util.Resource
import javax.inject.Inject

class GetRatesUseCaseImpl @Inject constructor(
    private val ratesRepository: RatesRepository
) : GetRatesUseCase {

    override suspend fun getRates(base: String): List<Currency> {
        return when (val response = ratesRepository.getRates(base)) {
            is Resource.Error -> listOf()
            is Resource.Success -> mappingCurrencys(response.data.rates)
        }
    }

    private fun mappingCurrencys(rates: Rates): List<Currency> {
        return listOf(
            Currency("AUD", rates.AUD),
            Currency("BRL", rates.BRL),
            Currency("CAD", rates.CAD),
            Currency("CHF", rates.CHF),
            Currency("CNY", rates.CNY),
            Currency("DKK", rates.DKK),
            Currency("EUR", rates.EUR),
            Currency("GBP", rates.GBP),
            Currency("HKD", rates.HKD),
            Currency("IDR", rates.IDR),
            Currency("INR", rates.INR),
            Currency("JPY", rates.JPY),
            Currency("KRW", rates.KRW),
            Currency("MXN", rates.MXN),
            Currency("MYR", rates.MYR),
            Currency("NOK", rates.NOK),
            Currency("NZD", rates.NZD),
            Currency("PLN", rates.PLN),
            Currency("RUB", rates.RUB),
            Currency("SEK", rates.SEK),
            Currency("SGD", rates.SGD),
            Currency("THB", rates.THB),
            Currency("TRY", rates.TRY),
            Currency("TWD", rates.TWD),
            Currency("USD", rates.USD),
            Currency("ZAR", rates.ZAR)
        )
    }
}
