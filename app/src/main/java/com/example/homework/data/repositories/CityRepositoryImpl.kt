package com.example.homework.data.repositories

import android.location.Location
import android.util.Log
import com.example.homework.data.api.ApiFactory
import com.example.homework.data.api.WeatherApi
import com.example.homework.data.db.dao.CityDao
import com.example.homework.data.db.models.CityDb
import com.example.homework.domain.CityRepository
import com.example.homework.data.models.CityData
import com.example.homework.domain.models.CityDomain
import kotlinx.coroutines.*
import java.lang.Exception

class CityRepositoryImpl(
    private val cityDao: CityDao,
    private val weatherApi: WeatherApi
) : CoroutineScope by CoroutineScope(Dispatchers.Main), CityRepository {

    override suspend fun getByCityName(name: String): CityDomain? {
        return try {
            val city: CityData = weatherApi.getWeather(name)

            cityDao.save(city.toCityDb())

            city.toCityDomain()
        } catch (exception: Exception) {
            cityDao.getCityByName(name)?.toCityDomain()
        }
    }

    override suspend fun getByCityId(id: Int): CityDomain? {
        return try {
            val city = weatherApi.getWeather(id)

            cityDao.save(city.toCityDb())

            city.toCityDomain()
        } catch (exception: Exception) {
            cityDao.getCityById(id)?.toCityDomain()
        }
    }

    override suspend fun getNearCities(location: Location?): List<CityDomain> {
        return try {
            location.let {
                val cities: List<CityData> =
                    if (it == null)
                        weatherApi.getNearCitiesInfo(0.0, 0.0).list
                    else
                        weatherApi.getNearCitiesInfo(lon = it.longitude, lat = it.latitude).list

                launch {
                    val normalCityInfo: ArrayList<CityDb> = arrayListOf()
                    cities.map {
                        if (it.id != null)
                            getByCityId(it.id!!)?.let {
                                normalCityInfo.add(it.toCityDb())
                            }
                    }
                    cityDao.save(normalCityInfo)
                }

                CityData.toCityDomainList(cities)
            }
        } catch (exception: Exception) {
            val cities: List<CityDb> = cityDao.getCities()
            Log.i("Cities", "${CityDb.toCityDomainList(cities)}")
            CityDb.toCityDomainList(cities)
        }
    }
}