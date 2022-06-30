package com.example.currencyconverterapp.ui.convert

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.data.repository.ConvertRepository
import com.example.currencyconverterapp.util.DispatchersProvider
import com.example.currencyconverterapp.util.Resource
import com.example.currencyconverterapp.util.ResourcesProvider
import com.example.currencyconverterapp.util.isSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConvertViewModel @Inject constructor(
    private val dispatchers: DispatchersProvider,
    private val resources: ResourcesProvider,
    private val convertRepository: ConvertRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<ConvertUiState>(ConvertUiState.Empty)
    val uiState: StateFlow<ConvertUiState> = _uiState.asStateFlow()

    fun convertCurrency(amount: String, from: String, to: String) {
        val fromAmount = amount.toDoubleOrNull()

        if (!isValidAmount(fromAmount)) return

        viewModelScope.launch(dispatchers.io) {
            _uiState.value = ConvertUiState.Loading

            val response = convertRepository.convertCurrency(
                amount = fromAmount ?: 1.0,
                from = from,
                to = to
            )
            when (response) {
                is Resource.Error -> {
                    _uiState.value = ConvertUiState.Failure(response.message.orEmpty())
                }
                is Resource.Success -> {
                    if (response.isSuccess) {
                        _uiState.value = ConvertUiState.Success(
                            "$amount $from = ${response.data.result} $to"
                        )
                    } else {
                        _uiState.value = ConvertUiState.Failure("Unexpected error")
                    }
                }
            }
        }
    }

    private fun isValidAmount(amount: Double?): Boolean {
        var isValid = true

        if (amount == null) {
            isValid = false
            _uiState.value = ConvertUiState.Failure("Not a valid amount")
        } else if (amount == 0.0) {
            isValid = false
            _uiState.value = ConvertUiState.Failure("Amount can't be zero")
        }

        return isValid
    }

    @SuppressLint("ResourceType")
    fun getCurrencys(): Array<out String> {
        return resources.getStringArray(R.array.currency_codes)
    }
}
