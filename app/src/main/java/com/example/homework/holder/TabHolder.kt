package com.example.homework.holder

import android.annotation.SuppressLint
import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.homework.R
import com.example.homework.models.Tab

class TabHolder(
    containerView: View,
    val click: (tab: Tab) -> Unit,
    val removeClick: (id: Int) -> Unit,
    val longTouch: (desc: String) -> Unit
) : RecyclerView.ViewHolder(containerView) {
    var id = itemView.findViewById<TextView>(R.id.id)
    var title = itemView.findViewById<TextView>(R.id.title)
    var remove = itemView.findViewById<ImageView>(R.id.remove)

    @SuppressLint("ClickableViewAccessibility")
    fun bind(tab: Tab) {
        id.text = tab.id.toString()
        title.text = tab.title

        remove.setOnClickListener {
            removeClick(tab.id)
        }

        itemView.setOnClickListener {
            click(tab)
        }

        itemView.setOnLongClickListener {
            longTouch(tab.desc)
            return@setOnLongClickListener true
        }
    }
}