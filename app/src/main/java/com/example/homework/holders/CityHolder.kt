package com.example.homework.holders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.homework.R
import com.example.homework.models.City

class CityHolder(
    containerView: View,
    private val click: (City) -> Unit
) : RecyclerView.ViewHolder(containerView) {
    var cityName: TextView = itemView.findViewById(R.id.city_name)
    var cityTemp: TextView = itemView.findViewById(R.id.city_temp)

    fun bind(city : City) {
        cityName.text = city.name
        cityTemp.text = "${city.main?.temp}"

        itemView.setOnClickListener {
            click(city)
        }
    }
}