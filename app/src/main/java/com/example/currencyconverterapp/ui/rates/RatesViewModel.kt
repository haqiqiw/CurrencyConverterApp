package com.example.currencyconverterapp.ui.rates

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.domain.model.Currency
import com.example.currencyconverterapp.domain.usecase.GetRatesUseCase
import com.example.currencyconverterapp.util.DispatchersProvider
import com.example.currencyconverterapp.util.ResourcesProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RatesViewModel @Inject constructor(
    private val dispatchers: DispatchersProvider,
    private val resources: ResourcesProvider,
    private val getRatesUseCase: GetRatesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<RatesUiState>(RatesUiState.Empty)
    val uiState: StateFlow<RatesUiState> = _uiState.asStateFlow()

    fun getRates(base: String) {
        viewModelScope.launch(dispatchers.io) {
            _uiState.value = RatesUiState.Loading

            val currencys = getRatesUseCase.getRates(base)
            if (currencys.isEmpty()) {
                _uiState.value = RatesUiState.Empty
            } else {
                _uiState.value = RatesUiState.Success(currencys)
            }
        }
    }

    @SuppressLint("ResourceType")
    fun getCurrencys(): Array<out String> {
        return resources.getStringArray(R.array.currency_codes)
    }

    fun getSelectedRate(base: String, currency: Currency): String {
        return "1 $base = ${currency.rate} ${currency.name}"
    }
}
