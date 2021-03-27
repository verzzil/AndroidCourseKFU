package com.example.homework.presenation.main

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import com.example.homework.R
import com.example.homework.data.adapters.CityAdapter
import com.example.homework.data.api.ApiFactory
import com.example.homework.data.db.AppDatabase
import com.example.homework.data.db.dao.CityDao
import com.example.homework.data.models.CityData
import com.example.homework.data.repositories.CityRepositoryImpl
import com.example.homework.data.repositories.LocationRepository
import com.example.homework.domain.GetCitiesUseCase
import com.example.homework.domain.GetDestinationUseCase
import com.example.homework.domain.models.CityDomain
import com.example.homework.presenation.FullWeatherInfoActivity
import com.example.homework.presenation.models.CityPresenter
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private var rvAdapter: CityAdapter? = null

    private val locationRequestCode = 100

    private lateinit var getCitiesUseCase: GetCitiesUseCase
    private lateinit var getDestinationUseCase: GetDestinationUseCase
    private lateinit var db: AppDatabase
    private lateinit var cityDao: CityDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = AppDatabase(applicationContext)
        cityDao = db.getCityDao()
        getCitiesUseCase = GetCitiesUseCase(
            CityRepositoryImpl(
                cityDao,
                ApiFactory.weatherApi
            )
        )
        getDestinationUseCase = GetDestinationUseCase(
            locationRepository = LocationRepository(
                FusedLocationProviderClient(applicationContext)
            )
        )

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
                        val currentCity =
                            getCitiesUseCase.getCityByName(search_view.query.toString())

                        if (currentCity != null) startFullInfoActivity(currentCity)
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

    private fun startFullInfoActivity(currentCity: CityPresenter?) {
        startActivity(
            Intent(
                this@MainActivity,
                FullWeatherInfoActivity::class.java
            ).apply {
                putExtra("city_id", currentCity?.id)
            }
        )
    }

    private fun initializeMainLogic() {
        lifecycleScope.launch {
            val location: Location? = try {
                getDestinationUseCase.getLocation()
            } catch (exception: Exception) {
                null
            }
            val nearCities = getCitiesUseCase.getNearCities(location)

            Log.i("nearcitites", "${nearCities}")

            rvAdapter = CityAdapter(nearCities as ArrayList<CityPresenter>) {
                startFullInfoActivity(it)
            }

            rv_cities.adapter = rvAdapter
        }
    }

}