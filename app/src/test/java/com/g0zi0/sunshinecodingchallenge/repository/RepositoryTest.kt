package com.g0zi0.sunshinecodingchallenge.repository

import androidx.test.core.app.ApplicationProvider
import com.g0zi0.sunshinecodingchallenge.Resource
import com.g0zi0.sunshinecodingchallenge.api.NetworkService
import com.g0zi0.sunshinecodingchallenge.model.CurrentWeather
import com.g0zi0.sunshinecodingchallenge.model.Forecasts
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.robolectric.RobolectricTestRunner


@RunWith(RobolectricTestRunner::class)
class RepositoryTest {

<<<<<<< Updated upstream
    private val networkService = mockk<NetworkService>()
    private val fakeCurrentWeather = mockk<CurrentWeather>()
    private val fakeForecasts = mockk<Forecasts>()
    private val repository = Repository(ApplicationProvider.getApplicationContext(), networkService.service())
=======
    private val weatherApi = mock(WeatherApi::class.java)
    private val fakeCurrentWeather = mockk<CurrentWeather>()
    private val fakeForecasts = mockk<Forecasts>()
    private val repository = Repository(ApplicationProvider.getApplicationContext(), weatherApi)

    @Before
    fun init() {
        mockkObject(NetworkService)
        every { NetworkService.service() } returns weatherApi
    }
>>>>>>> Stashed changes

    @Test
    fun `getCurrentWeather returns success`() {
        runBlocking {
<<<<<<< Updated upstream
            coEvery { networkService.service().getCurrentWeather("lat", "long", "imperial", "key") }returns fakeCurrentWeather
=======
            `when`(repository.weatherApi.getCurrentWeather("lat", "long", "imperial", "3aa158b2f14a9f493a8c725f8133d704")).thenReturn(fakeCurrentWeather)
>>>>>>> Stashed changes

            val result = repository.getCurrentWeather("lat", "lon")

            Assert.assertTrue(result is Resource.Success)
        }
    }

    @Test
    fun `getCurrentWeather returns error`() {
<<<<<<< Updated upstream
=======
        runBlocking {
            `when`(repository.weatherApi.getCurrentWeather("lat", "long", "imperial", "3aa158b2f14a9f493a8c725f8133d704")).thenReturn(null)


            val result = repository.getCurrentWeather("lat", "lon")
>>>>>>> Stashed changes

    }

    @Test
    fun `getForecasts returns success`() {
<<<<<<< Updated upstream
=======
        runBlocking {
            `when`(repository.weatherApi.getForecasts("lat", "long",10 ,"imperial", "3aa158b2f14a9f493a8c725f8133d704")).thenReturn(fakeForecasts)

            val result = repository.getCurrentWeather("lat", "lon")
>>>>>>> Stashed changes

    }

    @Test
    fun `getForecasts returns error`() {
<<<<<<< Updated upstream
=======
        runBlocking {
            `when`(repository.weatherApi.getForecasts("lat", "long",10 ,"imperial", "3aa158b2f14a9f493a8c725f8133d704")).thenReturn(null)

>>>>>>> Stashed changes

    }
}