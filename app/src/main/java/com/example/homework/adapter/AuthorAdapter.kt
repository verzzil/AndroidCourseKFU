package com.example.homework.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.homework.R
import com.example.homework.aidl.IMusicAidlInterface
import com.example.homework.holder.AuthorHolder
import com.example.homework.repository.AuthorMusicRepository
import com.example.homework.service.MusicService

class AuthorAdapter(
    private val musicService: IMusicAidlInterface,
    private val context: Context,
    private val click : (itemView: View) -> Unit
) : RecyclerView.Adapter<AuthorHolder>() {
    private val authorAndMusics = AuthorMusicRepository.authors

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AuthorHolder =
        AuthorHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_author, parent, false),
            musicService,
            context,
            click
        )

    override fun getItemCount(): Int =
        authorAndMusics.size

    override fun onBindViewHolder(holder: AuthorHolder, position: Int) =
        holder.bind(authorAndMusics[position])
}