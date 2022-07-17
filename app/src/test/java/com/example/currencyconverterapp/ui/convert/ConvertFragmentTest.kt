package com.example.currencyconverterapp.ui.convert

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.data.repository.ConvertRepository
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
class ConvertFragmentTest : RoboFragmentTest() {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var dispatchers: DispatchersProvider

    @Inject
    lateinit var resources: ResourcesProvider

    @Inject
    lateinit var convertRepository: ConvertRepository

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun `fragment should display initial view`() {
        // When
        launchFragmentInHiltContainer<ConvertFragment>()

        // Then
        onView(withId(R.id.etAmount)).check(matches(isDisplayed()))
        onView(withId(R.id.acFrom)).check(matches(isDisplayed()))
        onView(withId(R.id.acTo)).check(matches(isDisplayed()))
        onView(withId(R.id.btnConvert)).check(matches(isDisplayed()))
        onView(withText(R.string.convert)).check(matches(isDisplayed()))
        onView(withId(R.id.tvResult)).check(matches(isDisplayed()))
    }

    @Test
    fun `fragment when click button should display result view`() {
        // When
        launchFragmentInHiltContainer<ConvertFragment>()
        onView(withId(R.id.etAmount)).perform(typeText("1"))
        onView(withId(R.id.btnConvert)).perform(click())

        // Then
        onView(withId(R.id.tvResult)).check(matches(isDisplayed()))
        onView(withText("1 USD = 1000.0 IDR")).check(matches(isDisplayed()))
    }
}
