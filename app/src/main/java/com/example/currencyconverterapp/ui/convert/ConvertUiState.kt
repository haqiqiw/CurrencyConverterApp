package com.example.currencyconverterapp.ui.convert

sealed class ConvertUiState {
    class Success(val resultText: String) : ConvertUiState()
    class Failure(val errorText: String) : ConvertUiState()
    object Loading : ConvertUiState()
    object Empty : ConvertUiState()
}
