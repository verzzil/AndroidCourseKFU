package com.example.homework.data.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.homework.domain.models.CityDomain

@Entity(tableName = "city_db")
data class CityDb(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val windDirection: String,
    val pressure: Int,
    val sunrise: Long,
    val sunset: Long,
    val temp: Double,
    val tempDesc: String
) {
    fun toCityDomain(): CityDomain =
        CityDomain(
            id,
            name,
            tempDesc,
            pressure,
            sunrise,
            sunset,
            temp,
            tempDesc
        )

    companion object {
        fun toCityDomainList(cityDbList: List<CityDb>): List<CityDomain> =
            cityDbList.map {
                it.toCityDomain()
            }

    }
}