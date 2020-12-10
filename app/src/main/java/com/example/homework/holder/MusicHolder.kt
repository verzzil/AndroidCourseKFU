package com.example.homework.holder

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.homework.R
import com.example.homework.aidl.IMusicAidlInterface
import com.example.homework.model.Music


class MusicHolder(
    containerView: View,
    private val authorId: Int,
    private val musicService: IMusicAidlInterface,
    private val click: () -> Unit
) : RecyclerView.ViewHolder(containerView) {

    val musicTitle : TextView = itemView.findViewById(R.id.music_title)
    val musicDuration : TextView = itemView.findViewById(R.id.music_duration)

    fun bind(music : Music) {
        musicTitle.text = music.musicName

        itemView.setOnClickListener {
            musicService.playNewAuthor(authorId, music.id)
        }
    }

}