package com.example.homework.holder

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.homework.MainActivity
import com.example.homework.R
import com.example.homework.aidl.IMusicAidlInterface
import com.example.homework.model.Music
import com.example.homework.repository.AuthorMusicRepository
import kotlinx.android.synthetic.main.activity_main.*


class MusicHolder(
    containerView: View,
    private val authorId: Int,
    private val musicService: IMusicAidlInterface,
    private var context: Context,
    private val click: (itemView: View) -> Unit
) : RecyclerView.ViewHolder(containerView) {

    val musicTitle : TextView = itemView.findViewById(R.id.music_title)
    val musicDuration : TextView = itemView.findViewById(R.id.music_duration)

    fun bind(music : Music) {
        musicTitle.text = music.musicName

        if (!itemView.isSelected)
            itemView.findViewById<ImageView>(R.id.playing).visibility = View.GONE

        itemView.setOnClickListener {
            musicService.playNewAuthor(authorId, music.id)
            (context as MainActivity).changeTitles()
        }
    }

}