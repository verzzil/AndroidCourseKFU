package com.example.homework.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tab")
data class Tab(
    @PrimaryKey
    var id : Int,
    @ColumnInfo(name = "title")
    var title : String,
    @ColumnInfo(name = "description")
    var desc : String
) {
}