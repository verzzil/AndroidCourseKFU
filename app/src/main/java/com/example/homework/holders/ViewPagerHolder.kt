package com.example.homework.holders

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.homework.R

class ViewPagerHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var containerImage = itemView.findViewById<ImageView>(R.id.content_image)

    fun bind(image: Int) {
        containerImage.setImageResource(image)
    }
}