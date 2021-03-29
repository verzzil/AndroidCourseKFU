package com.example.homework.presenation.main

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.homework.R
import com.example.homework.data.adapters.CityAdapter
import com.example.homework.data.api.ApiFactory
import com.example.homework.data.db.AppDatabase
import com.example.homework.data.db.dao.CityDao
import com.example.homework.data.repositories.CityRepositoryImpl
import com.example.homework.data.repositories.LocationRepository
import com.example.homework.domain.GetCitiesUseCase
import com.example.homework.domain.GetDestinationUseCase
import com.example.homework.presenation.fullInfoActivity.FullWeatherInfoActivity
import com.example.homework.presenation.models.CityPresenter
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.android.synthetic.main.activity_main.*
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class MainActivity : MvpAppCompatActivity(), MainView {

    private val locationRequestCode = 100

    @InjectPresenter
    lateinit var presenter: MainPresenter

    @ProvidePresenter
    fun providePresenter(): MainPresenter = initPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

                presenter.searchCity(search_view.query.toString())

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }

    private fun initializeMainLogic() {
        presenter.initializeRvAdapter()
    }

    private fun initPresenter(): MainPresenter =
        MainPresenter(
            getCitiesUseCase = GetCitiesUseCase(
                CityRepositoryImpl(
                    AppDatabase(applicationContext).getCityDao(),
                    ApiFactory.weatherApi
                )
            ),
            getDestinationUseCase = GetDestinationUseCase(
                locationRepository = LocationRepository(
                    FusedLocationProviderClient(applicationContext)
                )
            )
        )

    override fun startFullInfoActivity(id: Int) {
        startActivity(
            Intent(
                this@MainActivity,
                FullWeatherInfoActivity::class.java
            ).apply {
                putExtra("city_id", id)
            }
        )
    }

    override fun makeToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun setRvAdapter(nearCities: ArrayList<CityPresenter>) {
        rv_cities.adapter = CityAdapter(nearCities) {
            presenter.onClickOnRv(it.id)
        }
    }

    override fun showLoader() {
        loader.visibility = View.VISIBLE
    }

    override fun hideLoader() {
        loader.visibility = View.GONE
    }

    override fun consumerError(throwable: Throwable) {
        TODO("Not yet implemented")
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

    override fun onDestroy() {
        presenter.destroy()
        super.onDestroy()
    }

}