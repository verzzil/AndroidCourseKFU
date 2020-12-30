package com.example.homework.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "tab")
data class Tab(
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0,
    @ColumnInfo(name = "title")
    var title : String,
    @ColumnInfo(name = "description")
    var desc : String
) : Serializable {
    constructor(title: String, desc: String) : this(id = 0, title = title, desc = desc)
}