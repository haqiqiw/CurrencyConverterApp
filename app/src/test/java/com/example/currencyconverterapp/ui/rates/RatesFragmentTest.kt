package com.example.currencyconverterapp.ui.rates

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.domain.usecase.GetRatesUseCase
import com.example.currencyconverterapp.launchFragmentInHiltContainer
import com.example.currencyconverterapp.ui.RoboFragmentTest
import com.example.currencyconverterapp.util.DispatchersProvider
import com.example.currencyconverterapp.util.ResourcesProvider
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class RatesFragmentTest : RoboFragmentTest() {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var dispatchers: DispatchersProvider

    @Inject
    lateinit var resources: ResourcesProvider

    @Inject
    lateinit var getRatesUseCase: GetRatesUseCase

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun `fragment should display initial view`() {
        // When
        launchFragmentInHiltContainer<RatesFragment>()

        // Then
        onView(withId(R.id.acCurrency)).check(matches(isDisplayed()))
        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()))
        onView(withText("IDR")).check(matches(isDisplayed()))
        onView(withText("1000.0")).check(matches(isDisplayed()))
        onView(withText("SGD")).check(matches(isDisplayed()))
        onView(withText("100.0")).check(matches(isDisplayed()))
    }
}
