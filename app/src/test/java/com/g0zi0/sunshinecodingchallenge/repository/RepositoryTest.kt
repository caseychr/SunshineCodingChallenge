package com.g0zi0.sunshinecodingchallenge.repository

import androidx.test.core.app.ApplicationProvider
import com.g0zi0.sunshinecodingchallenge.Resource
import com.g0zi0.sunshinecodingchallenge.api.NetworkService
import com.g0zi0.sunshinecodingchallenge.model.CurrentWeather
import com.g0zi0.sunshinecodingchallenge.model.Forecasts
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner


@RunWith(RobolectricTestRunner::class)
class RepositoryTest {

    private val networkService = mockk<NetworkService>()
    private val fakeCurrentWeather = mockk<CurrentWeather>()
    private val fakeForecasts = mockk<Forecasts>()
    private val repository = Repository(ApplicationProvider.getApplicationContext(), networkService.service())

    @Test
    fun `getCurrentWeather returns success`() {
        runBlocking {
            coEvery { networkService.service().getCurrentWeather("lat", "long", "imperial", "key") }returns fakeCurrentWeather

            val result = repository.getCurrentWeather("lat", "lon")

            Assert.assertTrue(result is Resource.Success)
        }
    }

    @Test
    fun `getCurrentWeather returns error`() {

    }

    @Test
    fun `getForecasts returns success`() {

    }

    @Test
    fun `getForecasts returns error`() {

    }
}