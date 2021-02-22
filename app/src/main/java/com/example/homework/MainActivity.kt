package com.example.homework

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.icu.util.GregorianCalendar
import android.icu.util.TimeZone
import android.icu.util.ULocale
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import com.example.homework.adapters.CityAdapter
import com.example.homework.api.ApiFactory
import com.example.homework.models.City
import com.example.homework.responses.CityWeatherResponse
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.mikepenz.iconics.Iconics
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.typeface.library.fontawesome.FontAwesome
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private lateinit var providerClient: FusedLocationProviderClient
    private var rvAdapter: CityAdapter? = null
    private val weatherApi = ApiFactory.weatherApi

    private val locationRequestCode = 100
    private var wayLatitude = 0.0
    private var wayLongitude = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        providerClient = LocationServices.getFusedLocationProviderClient(this)

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                locationRequestCode
            )

        } else {
            initializeMainLogic()
        }


        search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            @RequiresApi(Build.VERSION_CODES.N)
            override fun onQueryTextSubmit(query: String?): Boolean {
                lifecycleScope.launch {
                    try {
                        val currentCity = weatherApi.getWeather(search_view.query.toString())

                        startFullInfoActivity(currentCity)
                    } catch (e: Exception) {
                        Toast.makeText(
                            this@MainActivity,
                            "Введитет корректное название города",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            100 -> {
                if (
                    grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    initializeMainLogic()
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun startFullInfoActivity(currentCity: CityWeatherResponse) {
        startActivity(
            Intent(
                this@MainActivity,
                FullWeatherInfoActivity::class.java
            ).apply {
                putExtra("city_id", currentCity.id)
            })
    }

    private fun startFullInfoActivity(currentCity: City) {
        startActivity(
            Intent(
                this@MainActivity,
                FullWeatherInfoActivity::class.java
            ).apply {
                putExtra("city_id", currentCity.id)
            })
    }

    private fun initializeMainLogic() {
        providerClient.lastLocation.addOnSuccessListener {
            if (it != null) {
                wayLatitude = it.latitude
                wayLongitude = it.longitude
            }

            lifecycleScope.launch {
                val nearCitiesResponse =
                    weatherApi.getNearCitiesInfo(wayLatitude.toInt(), wayLongitude.toInt())
                val nearCities = nearCitiesResponse.list

                rvAdapter = CityAdapter(nearCities as ArrayList<City>) {
                    startFullInfoActivity(it)
                }

                rv_cities.adapter = rvAdapter

            }

        }
    }

}