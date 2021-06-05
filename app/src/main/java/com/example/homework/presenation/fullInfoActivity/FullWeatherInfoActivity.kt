package com.example.homework.presenation.fullInfoActivity

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.homework.R
import com.example.homework.data.api.ApiFactory
import com.example.homework.data.db.AppDatabase
import com.example.homework.data.db.dao.CityDao
import com.example.homework.data.repositories.CityRepositoryImpl
import com.example.homework.domain.GetCitiesUseCase
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.typeface.IIcon
import com.mikepenz.iconics.utils.toIconicsColor
import kotlinx.android.synthetic.main.activity_full_weather_info.*
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class FullWeatherInfoActivity : MvpAppCompatActivity(), FullWeatherInfoView {

    @InjectPresenter
    lateinit var presenter: FullWeatherInfoPresenter

    @ProvidePresenter
    fun providePresenter(): FullWeatherInfoPresenter = initPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_weather_info)

        presenter.chooseBackground()

        presenter.showInfoAboutCity()
    }

    override fun setBackground(color: Int) {
        main.setBackgroundResource(color)
    }

    override fun setWindDirectionIcon(icon: IIcon) {
        wind_icon.let {
            it.icon = IconicsDrawable(this).icon(icon)
            it.icon?.color(Color.WHITE.toIconicsColor())
        }
    }

    override fun setWeatherIcon(icon: IIcon) {
        weather_icon.let {
            it.icon = IconicsDrawable(this).icon(icon)
            it.icon?.color(Color.WHITE.toIconicsColor())
        }
    }

    override fun showCityName(name: String) {
        city_name.text = name
    }

    override fun showCityTemp(temp: String) {
        city_temp.text = temp
    }

    override fun showCityWeatherDesc(weatherDesc: String) {
        city_weather_desc.text = weatherDesc
    }

    override fun showWeekDay(weekDay: String) {
        week_day.text = weekDay
    }

    override fun showSunriseTime(sunriseTime: String) {
        sunrise.text = sunriseTime
    }

    override fun showSunsetTime(sunsetTime: String) {
        sunset.text = sunsetTime
    }

    override fun showWindDirection(windDirection: String) {
        wind.text = windDirection
    }

    override fun showPressure(pressure: String) {
        barometer.text = pressure
    }

    override fun showLoader() {
        loader.visibility = View.VISIBLE
    }

    override fun hideLoader() {
        loader.visibility = View.GONE
    }

    private fun initPresenter(): FullWeatherInfoPresenter =
        FullWeatherInfoPresenter(
            getCitiesUseCase = GetCitiesUseCase(
                CityRepositoryImpl(
                    AppDatabase(applicationContext).getCityDao(),
                    ApiFactory.weatherApi
                )
            ),
            cityId = intent.getIntExtra("city_id", -1)
        )
}