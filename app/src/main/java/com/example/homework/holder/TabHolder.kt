package com.example.homework.holder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.homework.R
import com.example.homework.models.Tab

class TabHolder(
    containerView : View
) : RecyclerView.ViewHolder(containerView){
    var id = itemView.findViewById<TextView>(R.id.id)
    var title = itemView.findViewById<TextView>(R.id.title)

    fun bind(tab : Tab) {
        id.text = tab.id.toString()
        title.text = tab.title


    }
}