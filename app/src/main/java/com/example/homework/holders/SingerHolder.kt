package com.example.homework.holders

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.homework.R
import com.example.homework.consts.Consts
import com.example.homework.data.Singers
import com.example.homework.models.Singer

class SingerHolder(
    containerView: View,
    private val itemClick: (Int) -> Unit,
    private val removeClick: (Int) -> Unit
) : RecyclerView.ViewHolder(containerView) {

    var name = itemView.findViewById<TextView>(R.id.item_title)
    var desc = itemView.findViewById<TextView>(R.id.item_description)
    var photo = itemView.findViewById<ImageView>(R.id.item_image)
    var like = itemView.findViewById<ImageView>(R.id.item_like)
    var remove = itemView.findViewById<ImageView>(R.id.item_remove)

    fun bind(singer: Singer) {
        name.text = singer.name
        desc.text = singer.description
        photo.setImageResource(singer.photo)
        like.setImageResource(singer.like)


        like.setOnClickListener {
            itemClick(Singers.findIndexById(singer.id))
        }
        remove.setOnClickListener {
            removeClick(Singers.findIndexById(singer.id))
        }
    }

    fun updateFields(bundle: Bundle) {
        if (bundle.containsKey(Consts.KEY_PHOTO)) {
            bundle.getInt(Consts.KEY_PHOTO).also {
                like.setImageResource(it)
            }
        }
    }


}