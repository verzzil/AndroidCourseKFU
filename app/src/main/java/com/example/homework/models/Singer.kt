package com.example.homework.models

data class Singer(
    val id: Int,
    val name: String,
    val description: String,
    val photo: Int,
    var like: Int
) {

    fun clone() : Singer =
        Singer(this.id, this.name, this.description, this.photo, this.like)

}