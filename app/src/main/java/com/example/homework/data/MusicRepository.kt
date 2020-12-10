package com.example.homework.data

import com.example.homework.R
import com.example.homework.models.Music

object MusicRepository {
    var music = arrayListOf(
        Music(0,"MORGENSHTERN", R.drawable.morgen, "WATAFUK?!", R.raw.morgenshtern_watafuk),
        Music(1,"FACE", R.drawable.face, "Юморист", R.raw.face_h),
        Music(2,"Slava Marlow", R.drawable.slava_marlow, "Снова я напиваюсь", R.raw.slava_marlow_buhayu),
        Music(3,"Fly Project", R.drawable.fly_project, "Musica", R.raw.fly_project_musica),
        Music(4,"Ellie Goulding", R.drawable.golding, "Outside", R.raw.golding_outside),
        Music(5,"Slava Marlow", R.drawable.slava_marlow, "По глазам", R.raw.slava_marlow_eye),
        Music(6,"PSY", R.drawable.psy, "Gangnam Style", R.raw.psy_gangnam_style),
        Music(7,"MORGENSHTERN feat Элджей", R.drawable.morgen, "Cadillac", R.raw.morgen_eld_cadillac),
        Music(8,"PSY", R.drawable.psy, "Gentleman", R.raw.psy_gentleman),
        Music(9,"Dabro", R.drawable.dabro, "Юность", R.raw.dabro_yu),
        Music(10,"Enrique Iglesias", R.drawable.iglesias, "Bailando", R.raw.iglesias_bailando),
        Music(11,"Fly Project", R.drawable.fly_project, "Mandala", R.raw.fly_project_mandala),
        Music(12,"Fly Project", R.drawable.fly_project, "Toca toca", R.raw.flyproject_toca)
    )

    private var maxIndex = music.size - 1
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