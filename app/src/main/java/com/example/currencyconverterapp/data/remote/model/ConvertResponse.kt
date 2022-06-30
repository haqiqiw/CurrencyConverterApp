package com.example.currencyconverterapp.data.remote.model

data class ConvertResponse(
    val date: String,
    val historical: Boolean,
    val info: Info,
    val query: Query,
    val result: Double,
    val success: Boolean
)
