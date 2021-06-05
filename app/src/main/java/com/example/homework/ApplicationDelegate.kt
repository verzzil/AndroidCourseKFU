package com.example.homework

import android.app.Application
import moxy.MvpFacade

class ApplicationDelegate: Application() {
    override fun onCreate() {
        super.onCreate()
        MvpFacade.init()
    }
}