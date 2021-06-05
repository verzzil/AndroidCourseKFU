package com.example.homework.presenation.models

data class CityPresenter(
    val id: Int = 0,
    val name: String,
    val windDirection: String,
    val pressure: Int,
    val sunrise: String,
    val sunset: String,
    val temp: String,
    val tempDesc: String
)
