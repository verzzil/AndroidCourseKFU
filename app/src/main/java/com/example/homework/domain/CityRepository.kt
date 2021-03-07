package com.example.homework.domain

import android.location.Location
import com.example.homework.domain.models.CityDomain

interface CityRepository {
    suspend fun getByCityName(name: String): CityDomain?
    suspend fun getByCityId(id: Int): CityDomain?
    suspend fun getNearCities(location: Location?): List<CityDomain>
}