package com.example.currencyconverterapp.data.remote.model

data class Query(
    val amount: Int,
    val from: String,
    val to: String
)
