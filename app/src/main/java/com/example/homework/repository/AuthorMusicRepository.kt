package com.example.homework.repository

import com.example.homework.R
import com.example.homework.model.Author
import com.example.homework.model.Music

object AuthorMusicRepository {
    val authors = arrayListOf(
        Author(
            0,
            "MORGENSHTERN",
            R.drawable.morgen,
            arrayListOf(
                Music(0, 0, "Новый Мерин", R.raw.morgen_new_merin),
                Music(0, 1, "WATAFUK?!", R.raw.morgen_watafuc)
            )
        ),
        Author(
            1,
            "Slava Marlow",
            R.drawable.slava,
            arrayListOf(
                Music(1,0,"Снова я напиваюсь", R.raw.slava_buhayu),
                Music(1,1,"По глазам", R.raw.slava_glaza),
                Music(1,2,"Быстро", R.raw.slava_bystro)
            )
        )
    )

    var currentAuthor = 0
    var currentMusic = 0
    var currentMaxAlbumSize = authors[currentAuthor].musics.size - 1

    fun getCurrentMusic(): Music =
        authors[currentAuthor].musics[currentMusic]

    fun getNext(): Music {
        if (currentMusic == currentMaxAlbumSize)
            currentMusic = 0
        else
            currentMusic++
        return getCurrentMusic()
    }

    fun getPrev(): Music {
        if (currentMusic == 0)
            currentMusic = currentMaxAlbumSize
        else
            currentMusic--
        return getCurrentMusic()
    }

}