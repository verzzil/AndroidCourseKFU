package com.example.homework.presenation.main

import android.location.Location
import com.example.homework.domain.GetCitiesUseCase
import com.example.homework.domain.GetDestinationUseCase
import com.example.homework.presenation.models.CityPresenter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import moxy.MvpPresenter
import java.lang.Exception

class MainPresenter(
    private val getCitiesUseCase: GetCitiesUseCase,
    private val getDestinationUseCase: GetDestinationUseCase
) : MvpPresenter<MainView>(), CoroutineScope by MainScope() {

    fun searchCity(query: String) {
        launch {
            viewState.showLoader()
            try {
                val currentCity =
                    getCitiesUseCase.getCityByName(query)

                if (currentCity != null)
                    viewState.startFullInfoActivity(currentCity.id)
                else
                    viewState.makeToast("Такого города нет в базе, а значит нет на свете")
            } catch (e: Exception) {
                viewState.makeToast("Введитет корректное название города")
            } finally {
                viewState.hideLoader()
            }
        }
    }

    fun initializeRvAdapter() {
        launch {
            viewState.showLoader()
            val location: Location? = try {
                getDestinationUseCase.getLocation()
            } catch (exception: Exception) {
                null
            }
            val nearCities = getCitiesUseCase.getNearCities(location) as ArrayList<CityPresenter>

            viewState.setRvAdapter(nearCities)

            viewState.hideLoader()
        }
    }

    fun onClickOnRv(id: Int) {
        viewState.showLoader()
        viewState.startFullInfoActivity(id)
        viewState.hideLoader()
    }

    fun destroy() {
        cancel()
    }


}