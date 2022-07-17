package com.example.currencyconverterapp.data.repository

import com.example.currencyconverterapp.data.remote.model.ConvertResponse
import com.example.currencyconverterapp.data.remote.model.Info
import com.example.currencyconverterapp.data.remote.model.Query
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
class ConvertRepositoryImplTest {

    @get:Rule
    val testCoroutineRule: TestCoroutineRule = TestCoroutineRule()

    private lateinit var convertRepository: ConvertRepository

    private lateinit var currencyService: CurrencyService

    @Before
    fun setUp() {
        currencyService = mockk(relaxed = true)
        convertRepository = ConvertRepositoryImpl(currencyService)
    }

    @After
    fun tearDown() {
        clearMocks(currencyService)
    }

    @Test
    fun `convertCurrency throw exception is correct`() = testCoroutineRule.runTest {
        // Given
        val mockExceptionMessage = "Exception message"
        coEvery { currencyService.convertCurrency(any(), any(), any()) } throws Exception(mockExceptionMessage)

        // When
        val resource = convertRepository.convertCurrency(1.0, "USD", "IDR")
        advanceUntilIdle()

        // Then
        (resource as Resource.Error).message shouldBe mockExceptionMessage
    }

    @Test
    fun `convertCurrency error is correct`() = testCoroutineRule.runTest {
        // Given
        coEvery { currencyService.convertCurrency(any(), any(), any()) } returns Response.error(
            500,
            mockk(relaxed = true)
        )

        // When
        val resource = convertRepository.convertCurrency(1.0, "USD", "IDR")
        advanceUntilIdle()

        // Then
        (resource as Resource.Error).message shouldBe "Response.error()"
    }

    @Test
    fun `convertCurrency success is correct`() = testCoroutineRule.runTest {
        // Given
        val mockConvertResponse = ConvertResponse(
            "1-2-2022",
            false,
            Info(1.0),
            Query(1, "USD", "IDR"),
            1000.0,
            true
        )
        coEvery { currencyService.convertCurrency(any(), any(), any()) } returns Response.success(mockConvertResponse)

        // When
        val resource = convertRepository.convertCurrency(1.0, "USD", "IDR")
        advanceUntilIdle()

        // Then
        with((resource as Resource.Success).data) {
            date shouldBe mockConvertResponse.date
            historical shouldBe mockConvertResponse.historical
            info.rate shouldBe mockConvertResponse.info.rate
            query.amount shouldBe mockConvertResponse.query.amount
            query.from shouldBe mockConvertResponse.query.from
            query.to shouldBe mockConvertResponse.query.to
            result shouldBe mockConvertResponse.result
            success shouldBe mockConvertResponse.success
        }
    }
}
