package com.example.homework.api

import com.example.homework.models.City
import com.example.homework.responses.CityWeatherResponse
import com.example.homework.responses.NearCitiesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("weather?")
    suspend fun getWeather(
        @Query("q") cityName: String
    ): City

    @GET("weather?")
    suspend fun getWeather(
        @Query("id") cityId: Int
    ): City

    @GET("find?cnt=20")
    suspend fun getNearCitiesInfo(
        @Query("lat") lat: Int,
        @Query("lon") lon: Int
    ): NearCitiesResponse

}