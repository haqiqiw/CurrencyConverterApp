package com.example.currencyconverterapp.data.repository

import com.example.currencyconverterapp.data.remote.model.Rates
import com.example.currencyconverterapp.data.remote.model.RatesResponse
import com.example.currencyconverterapp.data.remote.service.CurrencyService
import com.example.currencyconverterapp.test.TestCoroutineRule
import com.example.currencyconverterapp.test.shouldBe
import com.example.currencyconverterapp.util.Resource
import io.mockk.clearMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response
import java.lang.Exception

@ExperimentalCoroutinesApi
class RatesRepositoryImplTest {

    @get:Rule
    val testCoroutineRule: TestCoroutineRule = TestCoroutineRule()

    private lateinit var ratesRepository: RatesRepository

    private lateinit var currencyService: CurrencyService

    @Before
    fun setUp() {
        currencyService = mockk(relaxed = true)
        ratesRepository = RatesRepositoryImpl(currencyService)
    }

    @After
    fun tearDown() {
        clearMocks(currencyService)
    }

    @Test
    fun `getRates throw exception is correct`() = testCoroutineRule.runTest {
        // Given
        val mockExceptionMessage = "Exception message"
        coEvery { currencyService.getRates(any()) } throws Exception(mockExceptionMessage)

        // When
        val resource = ratesRepository.getRates("USD")
        advanceUntilIdle()

        // Then
        (resource as Resource.Error).message shouldBe mockExceptionMessage
    }

    @Test
    fun `getRates error is correct`() = testCoroutineRule.runTest {
        // Given
        coEvery { currencyService.getRates(any()) } returns Response.error(
            500,
            mockk(relaxed = true)
        )

        // When
        val resource = ratesRepository.getRates("USD")
        advanceUntilIdle()

        // Then
        (resource as Resource.Error).message shouldBe "Response.error()"
    }

    @Test
    fun `getRates success is correct`() = testCoroutineRule.runTest {
        // Given
        val mockRatesResponse = RatesResponse(
            "USD",
            "1-2-2022",
            Rates(
                1.0,
                1.0,
                1.0,
                1.0,
                1.0,
                1.0,
                1.0,
                1.0,
                1.0,
                1.0,
                1.0,
                1.0,
                1.0,
                1.0,
                1.0,
                1.0,
                1.0,
                1.0,
                1.0,
                1.0,
                1.0,
                1.0,
                1.0,
                1.0,
                1.0,
                1.0
            ),
            true
        )
        coEvery { currencyService.getRates(any()) } returns Response.success(mockRatesResponse)

        // When
        val resource = ratesRepository.getRates("USD")
        advanceUntilIdle()

        // Then
        with((resource as Resource.Success).data) {
            date shouldBe mockRatesResponse.date
            rates.IDR shouldBe mockRatesResponse.rates.IDR
            rates.USD shouldBe mockRatesResponse.rates.USD
            success shouldBe mockRatesResponse.success
        }
    }
}
