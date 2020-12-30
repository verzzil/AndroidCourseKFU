package com.example.homework.model

data class Author(
    val id: Int,
    val authorName: String,
    val authorPhoto: Int,
    val musics: ArrayList<Music>
) {

}