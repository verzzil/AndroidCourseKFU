package com.example.homework.domain

import android.location.Location
import com.example.homework.data.db.dao.CityDao
import com.example.homework.data.repositories.CityRepositoryImpl
import com.example.homework.data.models.CityData
import com.example.homework.domain.models.CityDomain
import com.example.homework.presenation.models.CityPresenter

class GetCitiesUseCase(
    val cityRepository: CityRepository
) {

    suspend fun getCityByName(name: String): CityPresenter? =
        cityRepository.getByCityName(name)?.toCityPresenter()

    suspend fun getCityById(id: Int): CityPresenter? =
        cityRepository.getByCityId(id)?.toCityPresenter()

    suspend fun getNearCities(location: Location?): List<CityPresenter> =
        CityDomain.toCityPresenterList(cityRepository.getNearCities(location))

}