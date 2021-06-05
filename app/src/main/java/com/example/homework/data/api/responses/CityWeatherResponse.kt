package com.example.homework.data.api.responses

import com.google.gson.annotations.SerializedName

data class CityWeatherResponse(
    @SerializedName("id")
    var id: Int,
    @SerializedName("clouds")
    var clouds: Clouds,
    @SerializedName("cod")
    var cod: Int,
    @SerializedName("coord")
    var coord: Coord,
    @SerializedName("dt")
    var dt: Int,
    @SerializedName("main")
    var main: Main,
    @SerializedName("name")
    var name: String,
    @SerializedName("sys")
    var sys: Sys,
    @SerializedName("timezone")
    var timezone: Int,
    @SerializedName("visibility")
    var visibility: Int,
    @SerializedName("weather")
    var weather: List<Weather>,
    @SerializedName("wind")
    var wind: Wind
)