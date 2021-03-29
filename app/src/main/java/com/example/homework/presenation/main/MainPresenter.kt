package com.example.homework.presenation.main

import android.location.Location
import androidx.lifecycle.lifecycleScope
import com.example.homework.data.db.AppDatabase
import com.example.homework.data.db.dao.CityDao
import com.example.homework.domain.GetCitiesUseCase
import com.example.homework.domain.GetDestinationUseCase
import com.example.homework.presenation.models.CityPresenter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.lang.Exception

class MainPresenter(
    private val mainView: MainView,
    private val getCitiesUseCase: GetCitiesUseCase,
    private val getDestinationUseCase: GetDestinationUseCase
) : CoroutineScope by MainScope() {

    fun searchCity(query: String) {
        launch {
            mainView.showLoader()
            try {
                val currentCity =
                    getCitiesUseCase.getCityByName(query)

                if (currentCity != null)
                    mainView.startFullInfoActivity(currentCity.id)
                else
                    mainView.makeToast("Такого города нет в базе, а значит нет на свете")
            } catch (e: Exception) {
                mainView.makeToast("Введитет корректное название города")
            } finally {
                mainView.hideLoader()
            }
        }
    }

    fun initializeRvAdapter() {
        launch {
            mainView.showLoader()
            val location: Location? = try {
                getDestinationUseCase.getLocation()
            } catch (exception: Exception) {
                null
            }
            val nearCities = getCitiesUseCase.getNearCities(location) as ArrayList<CityPresenter>

            mainView.setRvAdapter(nearCities)

            mainView.hideLoader()
        }
    }

    fun onClickOnRv(id: Int) {
        mainView.showLoader()
        mainView.startFullInfoActivity(id)
        mainView.hideLoader()
    }


}