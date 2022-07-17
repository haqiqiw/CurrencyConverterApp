package com.example.currencyconverterapp.ui.rates

import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.domain.model.Currency
import com.example.currencyconverterapp.domain.usecase.FakeGetRatesUseCase
import com.example.currencyconverterapp.test.TestCoroutineRule
import com.example.currencyconverterapp.test.shouldBe
import com.example.currencyconverterapp.util.FakeDispatchersProvider
import com.example.currencyconverterapp.util.FakeResourcesProvider
import com.natpryce.hamkrest.sameInstance
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class RatesViewModelTest {

    @get:Rule
    val testCoroutineRule: TestCoroutineRule = TestCoroutineRule()

    private lateinit var viewModel: RatesViewModel

    private lateinit var dispatchersProvider: FakeDispatchersProvider
    private lateinit var resourcesProvider: FakeResourcesProvider
    private lateinit var fetRatesUseCase: FakeGetRatesUseCase

    @Before
    fun setUp() {
        dispatchersProvider = FakeDispatchersProvider(testCoroutineRule.getTestDispatcher())
        resourcesProvider = FakeResourcesProvider()
        fetRatesUseCase = FakeGetRatesUseCase()
        viewModel = RatesViewModel(dispatchersProvider, resourcesProvider, fetRatesUseCase)
    }

    @Test
    fun `getRates when data empty is correct`() = testCoroutineRule.runTest {
        // Given
        fetRatesUseCase.getRatesValue = listOf()

        // When
        viewModel.getRates("USD")
        advanceUntilIdle()

        // Then
        viewModel.uiState.value shouldBe sameInstance(RatesUiState.Empty)
    }

    @Test
    fun `getRates when data not empty is correct`() = testCoroutineRule.runTest {
        // Given
        fetRatesUseCase.getRatesValue = listOf(Currency("IDR", 1000.0), Currency("SGD", 100.0))

        // When
        viewModel.getRates("USD")
        advanceUntilIdle()

        // Then
        (viewModel.uiState.value as RatesUiState.Success).items.size shouldBe 2
    }

    @Test
    fun `getCurrencys is correct`() {
        // Given
        val mockCurrencys = arrayOf("USD", "IDR", "SGD")
        resourcesProvider.setStringArray(R.array.currency_codes, mockCurrencys)

        // When
        val currencys = viewModel.getCurrencys()

        // Then
        currencys shouldBe mockCurrencys
    }

    @Test
    fun `getSelectedRate is correct`() {
        // Given
        val mockBase = "USD"
        val mockCurrency = Currency("IDR", 1000.0)

        // When
        val rate = viewModel.getSelectedRate(mockBase, mockCurrency)

        // Then
        rate shouldBe "1 USD = ${mockCurrency.rate} ${mockCurrency.name}"
    }
}
