package com.example.homework.presenation.main

import com.example.homework.presenation.models.CityPresenter
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.Skip

@AddToEndSingle
interface MainView : MvpView {

    @Skip
    fun startFullInfoActivity(id: Int)

    fun makeToast(message: String)

    fun setRvAdapter(nearCities: ArrayList<CityPresenter>)

    @Skip
    fun showLoader()

    @Skip
    fun hideLoader()

    @Skip
    fun consumerError(throwable: Throwable)

}