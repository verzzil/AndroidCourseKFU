package com.example.homework

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BrendHolder(
    containerView: View,
    private val click: (Brend) -> Unit
) : RecyclerView.ViewHolder(containerView) {

    var logo = itemView.findViewById<ImageView>(R.id.logo)
    val title = itemView.findViewById<TextView>(R.id.title)
    val shortDisc = itemView.findViewById<TextView>(R.id.short_disc)

    fun bind(brend: Brend) {
        logo.setImageResource(brend.logo)
        title.text = brend.title
        shortDisc.text = brend.shortDisc

        itemView.setOnClickListener {
            click(brend)
        }
    }

}