package com.example.currencyconverterapp.ui.rates

import com.example.currencyconverterapp.domain.model.Currency

sealed class RatesUiState {
    class Success(val items: List<Currency>) : RatesUiState()
    object Loading : RatesUiState()
    object Empty : RatesUiState()
}
