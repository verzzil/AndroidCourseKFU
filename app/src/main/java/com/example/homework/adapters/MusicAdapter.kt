package com.example.homework.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.homework.R
import com.example.homework.hoders.MusicHolder
import com.example.homework.models.Music

class MusicAdapter(
    private val music: ArrayList<Music>,
    private val click: (music: Music) -> Unit
) : RecyclerView.Adapter<MusicHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicHolder =
        MusicHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_music, parent, false),
            click
        )

    override fun getItemCount(): Int = music.size

    override fun onBindViewHolder(holder: MusicHolder, position: Int) {
        holder.bind(music[position])
    }

}