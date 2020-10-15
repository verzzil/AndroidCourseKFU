package com.example.homework

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class BrendAdapter(
    private val click: (Brend) -> Unit
) : RecyclerView.Adapter<BrendHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrendHolder =
        BrendHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false),
            click
            )

    override fun getItemCount(): Int =
        Brends.brends.size

    override fun onBindViewHolder(holder: BrendHolder, position: Int) {
        holder.bind(Brends.brends[position])
    }
}