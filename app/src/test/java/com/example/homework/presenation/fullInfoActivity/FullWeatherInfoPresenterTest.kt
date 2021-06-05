package com.example.homework.presenation.fullInfoActivity

import com.example.homework.domain.GetCitiesUseCase
import com.example.homework.presenation.main.MainPresenter
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class FullWeatherInfoPresenterTest {

    @MockK
    lateinit var getCitiesUseCase: GetCitiesUseCase

    @MockK
    lateinit var viewState: `FullWeatherInfoView$$State`

    var cityId = 1

    private lateinit var presenter: FullWeatherInfoPresenter

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        presenter = spyk(FullWeatherInfoPresenter(getCitiesUseCase, cityId))
        presenter.setViewState(viewState)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun chooseBackground() {
        // Arrange
        val expectedColor = 1
        //Act
        presenter.chooseBackground()
        //Assert
        verify {
            viewState.showLoader()
            viewState.setBackground(expectedColor)
            viewState.hideLoader()
        }
    }

    @Test
    fun showInfoAboutCity() {
        //Arrange

        //Act
        presenter.showInfoAboutCity()
        //Assert
        verify {
            viewState.showLoader()
            viewState.showCityName("A")
            viewState.showCityTemp("A")
            viewState.showCityWeatherDesc("a")
            viewState.showWeekDay("A")
            viewState.showSunriseTime("A")
            viewState.showSunsetTime("A")
            viewState.showWindDirection("A")
            viewState.showPressure("A")
            viewState.hideLoader()
        }
    }
}