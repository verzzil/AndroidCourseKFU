package com.example.homework.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.homework.R
import com.example.homework.aidl.IMusicAidlInterface
import com.example.homework.holder.MusicHolder
import com.example.homework.model.Music

class MusicAdapter(
    private val musicList: ArrayList<Music>,
    private val authorId: Int,
    private val musicService: IMusicAidlInterface,
    private val click: () -> Unit
) : RecyclerView.Adapter<MusicHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicHolder =
        MusicHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_music, parent, false),
            authorId,
            musicService,
            click
        )

    override fun getItemCount(): Int =
        musicList.size

    override fun onBindViewHolder(holder: MusicHolder, position: Int) =
        holder.bind(musicList[position])

}