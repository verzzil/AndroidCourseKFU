package com.example.homework.presenation.main

import com.example.homework.presenation.models.CityPresenter


interface MainView {
    fun startFullInfoActivity(id: Int)
    fun makeToast(message: String)
    fun setRvAdapter(nearCities: ArrayList<CityPresenter>)
    fun showLoader()
    fun hideLoader()
}