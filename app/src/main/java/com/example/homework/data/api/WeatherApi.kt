package com.example.homework.data.api

import com.example.homework.data.models.CityData
import com.example.homework.data.api.responses.NearCitiesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("weather?")
    suspend fun getWeather(
        @Query("q") cityName: String
    ): CityData

    @GET("weather?")
    suspend fun getWeather(
        @Query("id") cityId: Int
    ): CityData

    @GET("find?cnt=20")
    suspend fun getNearCitiesInfo(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double
    ): NearCitiesResponse

}