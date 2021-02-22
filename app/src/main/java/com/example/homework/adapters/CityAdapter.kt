package com.example.homework.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.homework.R
import com.example.homework.holders.CityHolder
import com.example.homework.models.City
import kotlinx.android.synthetic.main.activity_main.view.*

class CityAdapter(
    private val cities : ArrayList<City>,
    private val click: (City) -> Unit
) : RecyclerView.Adapter<CityHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityHolder =
        CityHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_city, parent, false),
            click
        )

    override fun getItemCount(): Int =
        cities.size

    override fun onBindViewHolder(holder: CityHolder, position: Int) =
        holder.bind(cities[position])
}