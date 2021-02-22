package com.example.homework

import android.annotation.SuppressLint
import android.graphics.Color
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.icu.util.TimeZone
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.homework.api.ApiFactory
import com.example.homework.models.City
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.typeface.library.weathericons.WeatherIcons
import com.mikepenz.iconics.utils.toIconicsColor
import kotlinx.android.synthetic.main.activity_full_weather_info.*
import kotlinx.coroutines.launch
import java.util.*

class FullWeatherInfoActivity : AppCompatActivity() {

    val weatherApi = ApiFactory.weatherApi

    var cityId: Int = -1

    @SuppressLint("SimpleDateFormat")
    private val currentTime = SimpleDateFormat("H").format(Calendar.getInstance().time).toInt()

    @SuppressLint("SimpleDateFormat")
    private val weekDay = SimpleDateFormat("EEEE").format(Calendar.getInstance().time).toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_weather_info)

        cityId = intent.getIntExtra("city_id", -1)

        chooseBackground()

        lifecycleScope.launch {
            val currentCity = weatherApi.getWeather(cityId)

            determineWeatherIcon(currentCity)
            determineWindDirectionIcon(currentCity)

            city_name.text = currentCity.name
            city_temp.text = "${currentCity.main?.temp?.toInt()}°"
            city_weather_desc.text = currentCity.weather[0].description
            week_day.text = weekDay
            sunrise.text = getDate(currentCity.sys.sunrise)
            sunset.text = getDate(currentCity.sys.sunset)
            wind.text = currentCity.getRussianWindDirection()
            barometer.text = "${currentCity.main?.pressure ?: 0}"

        }
    }

    private fun chooseBackground() {
        when (currentTime) {
            23, 24, in 0..5 ->
                main.setBackgroundResource(R.drawable.night_gradient)
            in 5..12 ->
                main.setBackgroundResource(R.drawable.morning_gradient)
            in 12..17 ->
                main.setBackgroundResource(R.drawable.day_gradient)
            else ->
                main.setBackgroundResource(R.drawable.evening_gradient)
        }
    }

    private fun determineWindDirectionIcon(currentCity: City) {
        wind_icon.let {
            when (currentCity.getRussianWindDirection()) {
                "север" -> it.icon = IconicsDrawable(this).icon(WeatherIcons.Icon.wic_direction_up)
                "северо-восток" -> it.icon =
                    IconicsDrawable(this).icon(WeatherIcons.Icon.wic_direction_up_right)
                "восток" -> it.icon =
                    IconicsDrawable(this).icon(WeatherIcons.Icon.wic_direction_right)
                "юго-восток" -> it.icon =
                    IconicsDrawable(this).icon(WeatherIcons.Icon.wic_direction_down_right)
                "юг" -> it.icon = IconicsDrawable(this).icon(WeatherIcons.Icon.wic_direction_down)
                "юго-запад" -> it.icon =
                    IconicsDrawable(this).icon(WeatherIcons.Icon.wic_direction_down_left)
                "запад" -> it.icon =
                    IconicsDrawable(this).icon(WeatherIcons.Icon.wic_direction_left)
                "северо-запад" -> it.icon =
                    IconicsDrawable(this).icon(WeatherIcons.Icon.wic_direction_up_left)
            }
            it.icon?.color(Color.WHITE.toIconicsColor())
        }
    }

    private fun determineWeatherIcon(currentCity: City) {
        weather_icon.let {
            when (currentCity.weather[0].description) {
                "небольшой снег с дождём" -> it.icon = IconicsDrawable(this)
                    .icon(WeatherIcons.Icon.wic_rain_mix)
                "небольшой снег" -> it.icon = IconicsDrawable(this)
                    .icon(WeatherIcons.Icon.wic_snow)
                "снег" -> it.icon = IconicsDrawable(this)
                    .icon(WeatherIcons.Icon.wic_snow)
                "дождь" -> it.icon = IconicsDrawable(this)
                    .icon(WeatherIcons.Icon.wic_rain)
                "ясно" -> it.icon = IconicsDrawable(this)
                    .icon(WeatherIcons.Icon.wic_day_sunny)
                "пасмурно" -> it.icon = IconicsDrawable(this)
                    .icon(WeatherIcons.Icon.wic_cloud)
                "облачно с прояснениями" -> it.icon = IconicsDrawable(this)
                    .icon(WeatherIcons.Icon.wic_day_cloudy)
                else -> ""
            }
            it.icon?.color(Color.WHITE.toIconicsColor())
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun getDate(time: Long): String? {
        val date = Date(time * 1000L)
        val sdf = SimpleDateFormat("HH:mm")
        sdf.timeZone = TimeZone.getTimeZone("GMT+3")
        return sdf.format(date)
    }
}