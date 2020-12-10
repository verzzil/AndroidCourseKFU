package com.example.homework.hoders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.homework.R
import com.example.homework.models.Music

class MusicHolder(
    containerView: View,
    private val click: (music: Music) -> Unit
) : RecyclerView.ViewHolder(containerView) {
    private val albumImage: ImageView = itemView.findViewById(R.id.album_avatar)
    private val playing: ImageView = itemView.findViewById(R.id.playing)
    private val trackName: TextView = itemView.findViewById(R.id.track_name)
    private val trackAuthor: TextView = itemView.findViewById(R.id.track_author)
    fun bind(music: Music) {
        albumImage.setImageResource(music.albumImage)
        trackName.text = music.trackName
        trackAuthor.text = music.author

        itemView.setOnClickListener {
            click(music)
        }

    }

}