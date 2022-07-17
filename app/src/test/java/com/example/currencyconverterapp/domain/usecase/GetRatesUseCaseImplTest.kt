package com.example.currencyconverterapp.domain.usecase

import com.example.currencyconverterapp.data.repository.FakeRatesRepository
import com.example.currencyconverterapp.test.TestCoroutineRule
import com.example.currencyconverterapp.test.shouldBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class GetRatesUseCaseImplTest {

    @get:Rule
    val testCoroutineRule: TestCoroutineRule = TestCoroutineRule()

    private lateinit var useCase: GetRatesUseCase

    private lateinit var ratesRepository: FakeRatesRepository

    @Before
    fun setUp() {
        ratesRepository = FakeRatesRepository()
        useCase = GetRatesUseCaseImpl(ratesRepository)
    }

    @Test
    fun `getRates error is correct`() = testCoroutineRule.runTest {
        // Given
        ratesRepository.getRatesResource = FakeRatesRepository.resourceError()

        // When
        val rates = useCase.getRates("USD")
        advanceUntilIdle()

        // Then
        rates.size shouldBe 0
    }

    @Test
    fun `getRates success is correct`() = testCoroutineRule.runTest {
        // Given
        ratesRepository.getRatesResource = FakeRatesRepository.resourceSuccess()

        // When
        val rates = useCase.getRates("USD")
        advanceUntilIdle()

        // Then
        rates.size shouldBe 26
        rates.sumOf { it.rate } shouldBe 26.0
    }
}
