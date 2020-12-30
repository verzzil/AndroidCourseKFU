package com.example.homework.holder

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.RecyclerView
import com.example.homework.R
import com.example.homework.adapter.MusicAdapter
import com.example.homework.aidl.IMusicAidlInterface
import com.example.homework.model.Author

class AuthorHolder(
    containerView: View,
    private val musicService: IMusicAidlInterface,
    private val context: Context,
    private val click: (itemView: View) -> Unit
) : RecyclerView.ViewHolder(containerView) {

    val authorPhoto : ImageView = itemView.findViewById(R.id.author_avatar)
    val authorName : TextView = itemView.findViewById(R.id.author_name)
    val musics : RecyclerView = itemView.findViewById(R.id.musics)

    fun bind(author : Author) {
        authorName.text = author.authorName
        authorPhoto.setImageResource(author.authorPhoto)

        musics.adapter = MusicAdapter(
            author.musics,
            author.id,
            musicService,
            context
        ) {
        }

        itemView.setOnClickListener {
            click(itemView)
        }

    }

}