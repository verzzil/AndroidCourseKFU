package com.example.homework.data.models

import android.service.autofill.UserData
import com.example.homework.data.api.responses.*
import com.example.homework.data.db.models.CityDb
import com.example.homework.domain.models.CityDomain
import com.google.gson.annotations.SerializedName

data class CityData(
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

    fun toCityDb(): CityDb =
        CityDb(
            0,
            name,
            getRussianWindDirection(),
            main?.pressure ?: 0,
            sys.sunrise,
            sys.sunset,
            main?.temp ?: 0.0,
            weather[0].description
        )

    fun toCityDomain(): CityDomain =
        id?.let {
            CityDomain(
                it,
                name,
                getRussianWindDirection(),
                main?.pressure ?: 0,
                sys.sunrise,
                sys.sunset,
                main?.temp ?: 0.0,
                weather[0].description
            )
        } ?: CityDomain(
            0,
            name,
            getRussianWindDirection(),
            main?.pressure ?: 0,
            sys.sunrise,
            sys.sunset,
            main?.temp ?: 0.0,
            weather[0].description
        )

    companion object {
        fun toCityDomainList(cityDataList: List<CityData>): List<CityDomain> =
            cityDataList.map {
                it.toCityDomain()
            }

        fun toCityDbList(cityDataList: List<CityData>): List<CityDb> =
            cityDataList.map {
                it.toCityDb()
            }
    }
}