package com.example.homework.data

import com.example.homework.R
import com.example.homework.models.Music

object MusicRepository {
    var music = arrayListOf(
        Music(0,"MORGENSHTERN", R.drawable.morgen, "WATAFUK?!", R.raw.morgenshtern_watafuk),
        Music(1,"FACE", R.drawable.face, "Юморист", R.raw.face_h),
        Music(2,"MORGENSHTERN feat Элджей", R.drawable.morgen, "Cadillac", R.raw.morgen_eld_cadillac),
        Music(3,"Dabro", R.drawable.dabro, "Юность", R.raw.dabro_yu),
        Music(4,"Ellie Goulding", R.drawable.golding, "Outside", R.raw.golding_outside),
        Music(5,"Enrique Iglesias", R.drawable.iglesias, "Bailando", R.raw.iglesias_bailando)
    )

    var maxIndex = music.size - 1
    var currentIndex = 0

    fun getNext() : Music {
        if (currentIndex == maxIndex)
            currentIndex = 0
        else
            currentIndex++
        return getCurrentTrack()
    }

    fun getPrev() : Music {
        if (currentIndex == 0)
            currentIndex = maxIndex
        else
            currentIndex--
        return getCurrentTrack()
    }

    fun getCurrentTrack() : Music = music[currentIndex]



}