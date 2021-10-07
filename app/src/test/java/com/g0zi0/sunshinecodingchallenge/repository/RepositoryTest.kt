package com.g0zi0.sunshinecodingchallenge.repository

import androidx.test.core.app.ApplicationProvider
import com.g0zi0.sunshinecodingchallenge.Resource
import com.g0zi0.sunshinecodingchallenge.api.NetworkService
import com.g0zi0.sunshinecodingchallenge.api.WeatherApi
import com.g0zi0.sunshinecodingchallenge.model.CurrentWeather
import com.g0zi0.sunshinecodingchallenge.model.Forecasts
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config


@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE, sdk = [28])
class RepositoryTest {

    private val networkService = mockk<NetworkService>()
    private val weatherApi = mockk<WeatherApi>()
    private val fakeCurrentWeather = mockk<CurrentWeather>()
    private val fakeForecasts = mockk<Forecasts>()
    private val repository = Repository(ApplicationProvider.getApplicationContext(), NetworkService.service())

    @Test
    fun `getCurrentWeather returns success`() {
        runBlocking {
            coEvery { networkService.service() } returns weatherApi
            coEvery { networkService.service().getCurrentWeather("lat", "long", "imperial", "key") }returns fakeCurrentWeather

            val result = repository.getCurrentWeather("lat", "lon")

            Assert.assertTrue(result is Resource.Success)
        }
    }

    @Test
    fun `getCurrentWeather returns error`() {
        runBlocking {
            coEvery { networkService.service().getCurrentWeather("lat", "long", "imperial", "key") }returns fakeCurrentWeather

            val result = repository.getCurrentWeather("lat", "lon")

            Assert.assertTrue(result is Resource.Error)
        }
    }

    @Test
    fun `getForecasts returns success`() {
        runBlocking {
            coEvery { networkService.service().getForecasts("lat", "long", 10,  "imperial", "key") }returns fakeForecasts

            val result = repository.getCurrentWeather("lat", "lon")

            Assert.assertTrue(result is Resource.Success)
        }
    }

    @Test
    fun `getForecasts returns error`() {
        runBlocking {
            coEvery { networkService.service().getForecasts("lat", "long", 10, "imperial", "key") }returns fakeForecasts

            val result = repository.getCurrentWeather("lat", "lon")

            Assert.assertTrue(result is Resource.Error)
        }
    }
}