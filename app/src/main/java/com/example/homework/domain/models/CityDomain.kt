package com.example.homework.domain.models

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import android.icu.util.TimeZone
import com.example.homework.data.db.models.CityDb
import com.example.homework.presenation.models.CityPresenter
import java.util.*

data class CityDomain(
    val id: Int = 0,
    val name: String,
    val windDirection: String,
    val pressure: Int,
    val sunrise: Long,
    val sunset: Long,
    val temp: Double,
    val tempDesc: String
) {

    companion object {
        fun toCityPresenterList(cityDomainList: List<CityDomain>): List<CityPresenter> =
            cityDomainList.map {
                it.toCityPresenter()
            }
    }

    fun toCityDb(): CityDb =
        CityDb(
            0,
            name,
            windDirection,
            pressure,
            sunrise,
            sunset,
            temp,
            tempDesc
        )

    fun toCityPresenter(): CityPresenter =
        CityPresenter(
            id,
            name,
            windDirection,
            pressure,
            getDate(sunrise) ?: "",
            getDate(sunset) ?: "",
            temp.toInt().toString(),
            tempDesc
        )

    @SuppressLint("SimpleDateFormat")
    private fun getDate(time: Long): String? {
        val date = Date(time * 1000L)
        val sdf = SimpleDateFormat("HH:mm")
        sdf.timeZone = TimeZone.getTimeZone("GMT+3")
        return sdf.format(date)
    }

}