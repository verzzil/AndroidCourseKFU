package com.example.homework.data.holders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.homework.R
import com.example.homework.data.models.CityData
import com.example.homework.domain.models.CityDomain
import com.example.homework.presenation.models.CityPresenter

class CityHolder(
    containerView: View,
    private val click: (CityPresenter) -> Unit
) : RecyclerView.ViewHolder(containerView) {
    var cityName: TextView = itemView.findViewById(R.id.city_name)
    var cityTemp: TextView = itemView.findViewById(R.id.city_temp)

    fun bind(city : CityPresenter) {
        cityName.text = city.name
        cityTemp.text = "${city.temp}"

        itemView.setOnClickListener {
            click(city)
        }
    }
}