package com.g0zi0.sunshinecodingchallenge.ui

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.LargeTest
import androidx.test.runner.AndroidJUnit4
import com.g0zi0.sunshinecodingchallenge.R
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Thread.sleep


@LargeTest
@RunWith(AndroidJUnit4::class)
class ForecastFragmentTest {

    lateinit var activityScenario: ActivityScenario<ForecastActivity>
    @Before
    fun init() {
        val intent = Intent(ApplicationProvider.getApplicationContext(), ForecastActivity::class.java)
        activityScenario = ActivityScenario.launch<ForecastActivity>(intent)
    }

    @Test
    fun scrollToBottomAndBackUp() {
        sleep(5000)
        onView(withId(R.id.forecastRecyclerView)).perform(swipeUp())
        onView(withId(R.id.forecastRecyclerView)).perform(swipeDown())
        onView(withId(R.id.cityTextView)).check(matches(isDisplayed()))
    }

    @Test
    fun expandAndCollapseForecasts() {
        sleep(5000)
        onView(withId(R.id.forecastRecyclerView)).perform(
            actionOnItemAtPosition<ForecastAdapter.ViewHolder>(
                1,
                click()
            )
        )
        sleep(3000)
        onView(withId(R.id.forecastRecyclerView)).perform(
            actionOnItemAtPosition<ForecastAdapter.ViewHolder>(
                1,
                click()
            )
        )
        onView(withId(R.id.forecastRecyclerView)).perform(swipeUp())
        onView(withId(R.id.forecastRecyclerView)).perform(
            actionOnItemAtPosition<ForecastAdapter.ViewHolder>(
                8,
                click()
            )
        )
        sleep(3000)
        onView(withId(R.id.forecastRecyclerView)).perform(
            actionOnItemAtPosition<ForecastAdapter.ViewHolder>(
                8,
                click()
            )
        )
        onView(withId(R.id.forecastRecyclerView)).perform(swipeDown())
    }
}