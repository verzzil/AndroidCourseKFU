package com.example.homework.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.homework.data.db.dao.CityDao
import com.example.homework.data.db.models.CityDb

@Database(entities = [CityDb::class], version = 3)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getCityDao() : CityDao

    companion object {
        private const val DATABASE_NAME = "user.db"

        @Volatile
        private var instance: AppDatabase? = null
        private val LOCK = Any()


        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context : Context) =
            Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
    }
}