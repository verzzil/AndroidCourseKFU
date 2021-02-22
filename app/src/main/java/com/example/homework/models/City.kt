package com.example.homework.models

import android.util.Log
import com.example.homework.responses.*
import com.google.gson.annotations.SerializedName

data class City(
    @SerializedName("clouds")
    var clouds: Clouds,
    @SerializedName("coord")
    var coord: Coord,
    @SerializedName("dt")
    var dt: Int?,
    @SerializedName("id")
    var id: Int?,
    @SerializedName("main")
    var main: Main?,
    @SerializedName("name")
    var name: String,
    @SerializedName("rain")
    var rain: Any,
    @SerializedName("snow")
    var snow: Snow,
    @SerializedName("sys")
    var sys: Sys,
    @SerializedName("weather")
    var weather: List<Weather>,
    @SerializedName("wind")
    var wind: Wind,
    @SerializedName("cod")
    var cod: Int,
    @SerializedName("timezone")
    var timezone: Int,
    @SerializedName("visibility")
    var visibility: Int
) {

    fun getRussianWindDirection(): String =
        when (wind.deg) {
            in 338..361, in 0..24 -> "север"
            in 24..69 -> "северо-восток"
            in 69..114 -> "восток"
            in 114..159 -> "юго-восток"
            in 159..204 -> "юг"
            in 204..249 -> "юго-запад"
            in 249..294 -> "запад"
            in 294..338 -> "северо-запад"
            else -> ""
        }

}