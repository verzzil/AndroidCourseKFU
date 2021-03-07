package com.example.homework.domain

import android.content.Context
import android.location.Location
import com.example.homework.data.repositories.LocationRepository
import com.google.android.gms.location.FusedLocationProviderClient

class GetDestinationUseCase(
    fusedLocationProviderClient: FusedLocationProviderClient
) {
    private var locationRepository : LocationRepository = LocationRepository(fusedLocationProviderClient)

    suspend fun getLocation() : Location =
        locationRepository.getLocation()
}