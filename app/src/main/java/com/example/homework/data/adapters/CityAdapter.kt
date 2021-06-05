package com.example.homework.data.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.homework.R
import com.example.homework.data.holders.CityHolder
import com.example.homework.data.models.CityData
import com.example.homework.domain.models.CityDomain
import com.example.homework.presenation.models.CityPresenter

class CityAdapter(
    private val cities : ArrayList<CityPresenter>,
    private val click: (CityPresenter) -> Unit
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