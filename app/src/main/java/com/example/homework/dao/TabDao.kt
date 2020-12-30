package com.example.homework.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.homework.models.Tab

@Dao
interface TabDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(tab : Tab)

    @Query("SELECT * FROM tab")
    suspend fun getTabs(): List<Tab>

    @Query("SELECT * FROM tab WHERE id = :id")
    suspend fun getTabById(id: Int)

    @Query("DELETE FROM tab WHERE id = :id")
    suspend fun deleteTabById(id: Int)

}