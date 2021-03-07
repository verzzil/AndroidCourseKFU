package com.example.homework.data.db.dao

import androidx.room.*
import com.example.homework.data.db.models.CityDb

@Dao
interface CityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(city: CityDb)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(cities: List<CityDb>)

    @Query("SELECT * FROM city_db")
    suspend fun getCities(): List<CityDb>

    @Query("SELECT * FROM city_db where id = :id limit 1")
    suspend fun getCityById(id: Int): CityDb?

    @Query("SELECT * FROM city_db where name LIKE :name limit 1")
    suspend fun getCityByName(name: String): CityDb?

    @Delete
    suspend fun deleteCity(city: CityDb)

}