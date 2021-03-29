package com.example.homework.presenation.fullInfoActivity

import com.mikepenz.iconics.typeface.IIcon
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.Skip

@AddToEndSingle
interface FullWeatherInfoView : MvpView {

    fun setBackground(color: Int)

    fun setWindDirectionIcon(icon: IIcon)

    fun setWeatherIcon(icon: IIcon)

    fun showCityName(name: String)

    fun showCityTemp(temp: String)

    fun showCityWeatherDesc(weatherDesc: String)

    fun showWeekDay(weekDay: String)

    fun showSunriseTime(sunriseTime: String)

    fun showSunsetTime(sunsetTime: String)

    fun showWindDirection(windDirection: String)

    fun showPressure(pressure: String)

    @Skip
    fun showLoader()

    @Skip
    fun hideLoader()


}