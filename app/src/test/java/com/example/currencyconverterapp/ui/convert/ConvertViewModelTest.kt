package com.example.currencyconverterapp.ui.convert

import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.data.repository.FakeConvertRepository
import com.example.currencyconverterapp.test.TestCoroutineRule
import com.example.currencyconverterapp.test.shouldBe
import com.example.currencyconverterapp.util.FakeDispatchersProvider
import com.example.currencyconverterapp.util.FakeResourcesProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ConvertViewModelTest {

    @get:Rule
    val testCoroutineRule: TestCoroutineRule = TestCoroutineRule()

    private lateinit var viewModel: ConvertViewModel

    private lateinit var dispatchersProvider: FakeDispatchersProvider
    private lateinit var resourcesProvider: FakeResourcesProvider
    private lateinit var convertRepository: FakeConvertRepository

    @Before
    fun setUp() {
        dispatchersProvider = FakeDispatchersProvider(testCoroutineRule.getTestDispatcher())
        resourcesProvider = FakeResourcesProvider()
        convertRepository = FakeConvertRepository()
        viewModel = ConvertViewModel(dispatchersProvider, resourcesProvider, convertRepository)
    }

    @Test
    fun `convertCurrency when amount not valid is correct`() {
        // When
        viewModel.convertCurrency("test", "USD", "IDR")

        // Then
        (viewModel.uiState.value as ConvertUiState.Failure).errorText shouldBe "Not a valid amount"
    }

    @Test
    fun `convertCurrency when amount zero is correct`() {
        // When
        viewModel.convertCurrency("0", "USD", "IDR")

        // Then
        (viewModel.uiState.value as ConvertUiState.Failure).errorText shouldBe "Amount can't be zero"
    }

    @Test
    fun `convertCurrency when resouce error is correct`() = testCoroutineRule.runTest {
        // Given
        convertRepository.convertCurrencyResource = FakeConvertRepository.resourceError()

        // When
        viewModel.convertCurrency("111", "USD", "IDR")
        advanceUntilIdle()

        // Then
        (viewModel.uiState.value as ConvertUiState.Failure).errorText shouldBe "Resource error"
    }

    @Test
    fun `convertCurrency when resouce success is correct`() = testCoroutineRule.runTest {
        // Given
        convertRepository.convertCurrencyResource = FakeConvertRepository.resourceSuccess()

        // When
        viewModel.convertCurrency("1", "USD", "IDR")
        advanceUntilIdle()

        // Then
        (viewModel.uiState.value as ConvertUiState.Success).resultText shouldBe "1 USD = 1000.0 IDR"
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
}
