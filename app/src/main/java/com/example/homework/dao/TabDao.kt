package com.example.homework.dao

import androidx.room.*
import com.example.homework.models.Tab

@Dao
interface TabDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(tab: Tab)

    @Update
    suspend fun update(tab: Tab)

    @Query("SELECT * FROM tab")
    suspend fun getTabs(): List<Tab>

    @Query("SELECT * FROM tab WHERE id = :id")
    suspend fun getTabById(id: Int): Tab

    @Query("DELETE FROM tab WHERE id = :id")
    suspend fun deleteTabById(id: Int)

    @Query("DELETE FROM tab")
    suspend fun deleteAll()

}