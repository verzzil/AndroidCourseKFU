package com.example.homework.presenation.main

import android.content.Context
import android.os.Build
import android.widget.SearchView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.homework.domain.GetCitiesUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.lang.Exception

class MainPresenter(
    val appContext: Context,
    val getCitiesUseCase: GetCitiesUseCase
) : CoroutineScope by MainScope() {
    fun onSubmitQueryText(searchView: SearchView): SearchView.OnQueryTextListener {
        return object : SearchView.OnQueryTextListener {
            @RequiresApi(Build.VERSION_CODES.N)
            override fun onQueryTextSubmit(query: String?): Boolean {
                launch {
                    try {
                        val currentCity =
                            getCitiesUseCase.getCityByName(searchView.query.toString())

                    } catch (e: Exception) {
                        Toast.makeText(
                            appContext,
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
        }
    }

}