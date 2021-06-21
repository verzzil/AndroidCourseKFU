package com.example.homework

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.homework.dao.TabDao
import com.example.homework.models.Tab

@Database(entities = [Tab::class], version = 1)
abstract class DataBase : RoomDatabase() {

    abstract fun getTabDAO() : TabDao

    companion object {
        private final val DATABASE_NAME = "user.db"

        @Volatile
        private var instance: DataBase? = null
        private val LOCK = Any()


        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context : Context) =
            Room.databaseBuilder(context, DataBase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
    }

}