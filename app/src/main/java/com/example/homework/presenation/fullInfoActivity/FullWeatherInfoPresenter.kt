package com.example.homework.presenation.fullInfoActivity

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import com.example.homework.R
import com.example.homework.domain.GetCitiesUseCase
import com.example.homework.presenation.models.CityPresenter
import com.mikepenz.iconics.typeface.library.weathericons.WeatherIcons
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import moxy.MvpPresenter

class FullWeatherInfoPresenter(
    private val getCitiesUseCase: GetCitiesUseCase,
    private val cityId: Int
) : MvpPresenter<FullWeatherInfoView>(), CoroutineScope by MainScope() {

    @SuppressLint("SimpleDateFormat")
    private val currentTime = SimpleDateFormat("H").format(Calendar.getInstance().time).toInt()

    @SuppressLint("SimpleDateFormat")
    private val weekDay = SimpleDateFormat("EEEE").format(Calendar.getInstance().time).toString()

    fun chooseBackground() {
        viewState.showLoader()
        when (currentTime) {
            23, 24, in 0..5 ->
                viewState.setBackground(R.drawable.night_gradient)
            in 5..12 ->
                viewState.setBackground(R.drawable.morning_gradient)
            in 12..17 ->
                viewState.setBackground(R.drawable.day_gradient)
            else ->
                viewState.setBackground(R.drawable.evening_gradient)
        }
        viewState.hideLoader()
    }

    fun showInfoAboutCity() {
        launch {
            viewState.showLoader()
            val currentCity = getCitiesUseCase.getCityById(cityId)

            determineWeatherIcon(currentCity)
            determineWindDirectionIcon(currentCity)

            if (currentCity != null) {
                viewState.showCityName(currentCity.name)
                viewState.showCityTemp("${currentCity.temp}°")
                viewState.showCityWeatherDesc(currentCity.tempDesc)
                viewState.showWeekDay(weekDay)
                viewState.showSunriseTime(currentCity.sunrise)
                viewState.showSunsetTime(currentCity.sunset)
                viewState.showWindDirection(currentCity.windDirection)
                viewState.showPressure("${currentCity.pressure}")
            }
            viewState.hideLoader()
        }
    }

    private fun determineWindDirectionIcon(currentCity: CityPresenter?) {
        when (currentCity?.windDirection) {
            "север" -> viewState.setWindDirectionIcon(WeatherIcons.Icon.wic_direction_up)
            "северо-восток" -> viewState.setWindDirectionIcon(WeatherIcons.Icon.wic_direction_up_right)
            "восток" -> viewState.setWindDirectionIcon(WeatherIcons.Icon.wic_direction_right)
            "юго-восток" -> viewState.setWindDirectionIcon(WeatherIcons.Icon.wic_direction_down_right)
            "юг" -> viewState.setWindDirectionIcon(WeatherIcons.Icon.wic_direction_down)
            "юго-запад" -> viewState.setWindDirectionIcon(WeatherIcons.Icon.wic_direction_down_left)
            "запад" -> viewState.setWindDirectionIcon(WeatherIcons.Icon.wic_direction_left)
            "северо-запад" -> viewState.setWindDirectionIcon(WeatherIcons.Icon.wic_direction_up_left)
        }
    }

    private fun determineWeatherIcon(currentCity: CityPresenter?) {
        when (currentCity?.tempDesc) {
            "небольшой снег с дождём" -> viewState.setWeatherIcon(WeatherIcons.Icon.wic_rain_mix)
            "небольшой снег" -> viewState.setWeatherIcon(WeatherIcons.Icon.wic_snow)
            "снег" -> viewState.setWeatherIcon(WeatherIcons.Icon.wic_snow)
            "дождь" -> viewState.setWeatherIcon(WeatherIcons.Icon.wic_rain)
            "ясно" -> viewState.setWeatherIcon(WeatherIcons.Icon.wic_day_sunny)
            "пасмурно" -> viewState.setWeatherIcon(WeatherIcons.Icon.wic_cloud)
            "облачно с прояснениями" -> viewState.setWeatherIcon(WeatherIcons.Icon.wic_day_cloudy)
            else -> ""
        }
    }

}