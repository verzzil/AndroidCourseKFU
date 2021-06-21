package com.example.homework.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity(tableName = "tab")
data class Tab(
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0,
    @ColumnInfo(name = "title")
    var title : String,
    @ColumnInfo(name = "description")
    var desc : String,
    @ColumnInfo(name = "timestamp")
    var date : Long,
    @ColumnInfo(name = "longitude")
    var longitude : Double,
    @ColumnInfo(name = "latitude")
    var latitude : Double
) : Serializable {
    constructor(title: String, desc: String, longitude: Double, latitude: Double) : this(id = 0, title = title, desc = desc, date = 0, longitude = longitude, latitude = latitude)
    constructor(title: String, desc: String, date: Long, longitude: Double, latitude: Double) : this(id = 0, title = title, desc = desc, date = date, longitude = longitude, latitude = latitude)
}