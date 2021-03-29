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

class FullWeatherInfoPresenter(
    private val view: FullWeatherInfoView,
    private val getCitiesUseCase: GetCitiesUseCase,
    private val cityId: Int
) : CoroutineScope by MainScope() {

    @SuppressLint("SimpleDateFormat")
    private val currentTime = SimpleDateFormat("H").format(Calendar.getInstance().time).toInt()

    @SuppressLint("SimpleDateFormat")
    private val weekDay = SimpleDateFormat("EEEE").format(Calendar.getInstance().time).toString()

    fun chooseBackground() {
        view.showLoader()
        when (currentTime) {
            23, 24, in 0..5 ->
                view.setBackground(R.drawable.night_gradient)
            in 5..12 ->
                view.setBackground(R.drawable.morning_gradient)
            in 12..17 ->
                view.setBackground(R.drawable.day_gradient)
            else ->
                view.setBackground(R.drawable.evening_gradient)
        }
        view.hideLoader()
    }

    fun showInfoAboutCity() {
        launch {
            view.showLoader()
            val currentCity = getCitiesUseCase.getCityById(cityId)

            determineWeatherIcon(currentCity)
            determineWindDirectionIcon(currentCity)

            if (currentCity != null) {
                view.showCityName(currentCity.name)
                view.showCityTemp("${currentCity.temp}°")
                view.showCityWeatherDesc(currentCity.tempDesc)
                view.showWeekDay(weekDay)
                view.showSunriseTime(currentCity.sunrise)
                view.showSunsetTime(currentCity.sunset)
                view.showWindDirection(currentCity.windDirection)
                view.showPressure("${currentCity.pressure}")
            }
            view.hideLoader()
        }
    }

    private fun determineWindDirectionIcon(currentCity: CityPresenter?) {
        when (currentCity?.windDirection) {
            "север" -> view.setWindDirectionIcon(WeatherIcons.Icon.wic_direction_up)
            "северо-восток" -> view.setWindDirectionIcon(WeatherIcons.Icon.wic_direction_up_right)
            "восток" -> view.setWindDirectionIcon(WeatherIcons.Icon.wic_direction_right)
            "юго-восток" -> view.setWindDirectionIcon(WeatherIcons.Icon.wic_direction_down_right)
            "юг" -> view.setWindDirectionIcon(WeatherIcons.Icon.wic_direction_down)
            "юго-запад" -> view.setWindDirectionIcon(WeatherIcons.Icon.wic_direction_down_left)
            "запад" -> view.setWindDirectionIcon(WeatherIcons.Icon.wic_direction_left)
            "северо-запад" -> view.setWindDirectionIcon(WeatherIcons.Icon.wic_direction_up_left)
        }
    }

    private fun determineWeatherIcon(currentCity: CityPresenter?) {
        when (currentCity?.tempDesc) {
            "небольшой снег с дождём" -> view.setWeatherIcon(WeatherIcons.Icon.wic_rain_mix)
            "небольшой снег" -> view.setWeatherIcon(WeatherIcons.Icon.wic_snow)
            "снег" -> view.setWeatherIcon(WeatherIcons.Icon.wic_snow)
            "дождь" -> view.setWeatherIcon(WeatherIcons.Icon.wic_rain)
            "ясно" -> view.setWeatherIcon(WeatherIcons.Icon.wic_day_sunny)
            "пасмурно" -> view.setWeatherIcon(WeatherIcons.Icon.wic_cloud)
            "облачно с прояснениями" -> view.setWeatherIcon(WeatherIcons.Icon.wic_day_cloudy)
            else -> ""
        }
    }

}