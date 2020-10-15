package com.example.homework

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.Group
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recycler_item.view.*

class BrendHolder(
    containerView: View,
    private val likeClick: (Brend, Int) -> Unit,
    private val click: (Brend) -> Unit
) : RecyclerView.ViewHolder(containerView) {

    var logo = itemView.findViewById<ImageView>(R.id.logo)
    val title = itemView.findViewById<TextView>(R.id.title)
    val shortDisc = itemView.findViewById<TextView>(R.id.short_disc)
    val countLikes = itemView.findViewById<TextView>(R.id.count_likes)
    val likeIcon = itemView.findViewById<ImageView>(R.id.like)
    val likeCount = itemView.findViewById<TextView>(R.id.count_likes)

    fun bind(brend: Brend) {
        logo.setImageResource(brend.logo)
        title.text = brend.title
        shortDisc.text = brend.shortDisc
        countLikes.text = "${brend.likes}"
        likeIcon.setImageResource(brend.likeIcon)

        likeIcon.setOnClickListener {
            likeClick(brend, layoutPosition)
        }
        likeCount.setOnClickListener {
            likeClick(brend, layoutPosition)
        }

        itemView.setOnClickListener {
            click(brend)
        }
    }

}