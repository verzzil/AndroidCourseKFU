package com.example.homework

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class BrendAdapter(
    private val brends: ArrayList<Brend>,
    private val likeClick: (Brend, Int) -> Unit,
    private val click: (Brend) -> Unit
) : RecyclerView.Adapter<BrendHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrendHolder =
        BrendHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false),
            likeClick,
            click
        )

    override fun getItemCount(): Int =
        brends.size

    override fun onBindViewHolder(holder: BrendHolder, position: Int) {
        holder.bind(brends[position])
    }

}