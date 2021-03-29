package com.example.homework.domain

import android.location.Location
import com.example.homework.data.repositories.LocationRepository

class GetDestinationUseCase(
    val locationRepository: LocationRepository
) {

    suspend fun getLocation() : Location =
        locationRepository.getLocation()
}