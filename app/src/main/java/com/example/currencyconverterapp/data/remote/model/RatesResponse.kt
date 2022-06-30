package com.example.currencyconverterapp.data.remote.model

data class RatesResponse(
    val base: String,
    val date: String,
    val rates: Rates,
    val success: Boolean
)
