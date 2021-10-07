package com.g0zi0.sunshinecodingchallenge.viewmodel

import androidx.test.core.app.ApplicationProvider
import com.g0zi0.sunshinecodingchallenge.Resource
import com.g0zi0.sunshinecodingchallenge.model.CurrentWeather
import com.g0zi0.sunshinecodingchallenge.model.Forecasts
import com.g0zi0.sunshinecodingchallenge.repository.Repository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE, sdk = [28])
class ViewModelTest {

    private val repository = mockk<Repository>()
    private val fakeCurrentWeather = mockk<CurrentWeather>()
    private val fakeForecasts = mockk<Forecasts>()
    private val viewModel = ForecastViewModel(ApplicationProvider.getApplicationContext())

    @Before
    fun init() {
        viewModel.repository = repository
    }

    @Test
    fun `getCurrentWeather returns success`() {
        runBlocking {
            coEvery { repository.getCurrentWeather("lat", "lon") } returns Resource.Success(fakeCurrentWeather)

            viewModel.getCurrentWeather("lat", "lon")

            Assert.assertTrue(viewModel.currentWeatherLiveData.value is Resource.Success)
        }
    }

    @Test
    fun `getCurrentWeather returns error`() {
        runBlocking {
            coEvery { repository.getCurrentWeather("lat", "lon") } returns Resource.Error(Throwable(""))

            viewModel.getCurrentWeather("lat", "lon")

            Assert.assertTrue(viewModel.currentWeatherLiveData.value is Resource.Error)
        }
    }

    @Test
    fun `getForecasts returns success`() {
        runBlocking {
            coEvery { repository.getForecasts("lat", "lon") } returns Resource.Success(fakeForecasts)

            viewModel.getCurrentWeather("lat", "lon")

            Assert.assertTrue(viewModel.forecastsLiveData.value is Resource.Success)
        }
    }

    @Test
    fun `getForecasts returns error`() {
        runBlocking {
            coEvery { repository.getForecasts("lat", "lon") } returns Resource.Error(Throwable(""))

            viewModel.getCurrentWeather("lat", "lon")

            Assert.assertTrue(viewModel.forecastsLiveData.value is Resource.Error)
        }
    }
}