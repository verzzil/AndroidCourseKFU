package com.example.homework.presenation.main

import android.location.Location
import com.example.homework.domain.GetCitiesUseCase
import com.example.homework.domain.GetDestinationUseCase
import com.example.homework.presenation.models.CityPresenter
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import moxy.viewstate.MvpViewState
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class MainPresenterTest {

    @MockK
    lateinit var getCitiesUseCase: GetCitiesUseCase

    @MockK
    lateinit var getDestinationUseCase: GetDestinationUseCase

    @MockK
    lateinit var location: Location

    @MockK(relaxed = true)
    lateinit var viewState: `MainView$$State`

    private lateinit var presenter: MainPresenter

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        presenter = spyk(MainPresenter(getCitiesUseCase, getDestinationUseCase))
        presenter.setViewState(viewState)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun searchCity() {
        // Arrange
        val expectedName = "Ufa"
        val expectedCityId = 17
        val expectedMessage = "Такого города нет в базе, а значит нет на свете"
        val expectedMessage2 = "Введитет корректное название города"

        coEvery { getCitiesUseCase.getCityByName(expectedName) } returns mockk {
            every { id } returns expectedCityId
        }
        // Act
        presenter.searchCity(expectedName)
        // Assert
        verify {
            viewState.showLoader()
            viewState.startFullInfoActivity(expectedCityId)
            viewState.hideLoader()
        }
        verify(inverse = true) {
            viewState.makeToast(expectedMessage)
            viewState.makeToast(expectedMessage2)
        }
    }

    @Test
    fun initializeRvAdapter() {
        // Arrange
        var expectedArrayList = arrayListOf<CityPresenter>()
        coEvery { getDestinationUseCase.getLocation() } returns mockk {
            location = this
        }
        coEvery { getCitiesUseCase.getNearCities(location) } returns mockk {
            expectedArrayList = this as ArrayList<CityPresenter>
        }
        // Act
        presenter.initializeRvAdapter()
        // Assert
        verify {
            viewState.showLoader()
            viewState.setRvAdapter(expectedArrayList)
            viewState.hideLoader()
        }

    }

    @Test
    fun onClickOnRv() {
        //Arrange
        val expectedId = 17
        //Act
        presenter.onClickOnRv(expectedId)
        //Assert
        verify {
            viewState.showLoader()
            viewState.startFullInfoActivity(expectedId)
            viewState.hideLoader()
        }
    }

}