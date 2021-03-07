package com.example.homework.presenation

import android.annotation.SuppressLint
import android.graphics.Color
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.icu.util.TimeZone
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.homework.R
import com.example.homework.data.api.ApiFactory
import com.example.homework.data.db.AppDatabase
import com.example.homework.data.db.dao.CityDao
import com.example.homework.data.models.CityData
import com.example.homework.domain.GetCitiesUseCase
import com.example.homework.domain.GetDestinationUseCase
import com.example.homework.presenation.models.CityPresenter
import com.google.android.gms.location.FusedLocationProviderClient
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.typeface.library.weathericons.WeatherIcons
import com.mikepenz.iconics.utils.toIconicsColor
import kotlinx.android.synthetic.main.activity_full_weather_info.*
import kotlinx.coroutines.launch
import java.util.*

class FullWeatherInfoActivity : AppCompatActivity() {

    private lateinit var getCitiesUseCase: GetCitiesUseCase
    private lateinit var getDestinationUseCase: GetDestinationUseCase
    private lateinit var db: AppDatabase
    private lateinit var cityDao: CityDao

    var cityId: Int = -1

    @SuppressLint("SimpleDateFormat")
    private val currentTime = SimpleDateFormat("H").format(Calendar.getInstance().time).toInt()

    @SuppressLint("SimpleDateFormat")
    private val weekDay = SimpleDateFormat("EEEE").format(Calendar.getInstance().time).toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_weather_info)

        db = AppDatabase(applicationContext)
        cityDao = db.getCityDao()
        getCitiesUseCase = GetCitiesUseCase(cityDao)
        getDestinationUseCase = GetDestinationUseCase(
            fusedLocationProviderClient = FusedLocationProviderClient(applicationContext)
        )

        cityId = intent.getIntExtra("city_id", -1)

        chooseBackground()

        lifecycleScope.launch {
            val currentCity = getCitiesUseCase.getCityById(cityId)

            determineWeatherIcon(currentCity)
            determineWindDirectionIcon(currentCity)

            if (currentCity != null) {
                city_name.text = currentCity.name
                city_temp.text = "${currentCity.temp}°"
                city_weather_desc.text = currentCity.tempDesc
                week_day.text = weekDay
                sunrise.text = currentCity.sunrise
                sunset.text = currentCity.sunset
                wind.text = currentCity.windDirection
                barometer.text = "${currentCity.pressure}"
            }
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

    private fun determineWindDirectionIcon(currentCity: CityPresenter?) {
        wind_icon.let {
            when (currentCity?.windDirection) {
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

    private fun determineWeatherIcon(currentCity: CityPresenter?) {
        weather_icon.let {
            when (currentCity?.tempDesc) {
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


}