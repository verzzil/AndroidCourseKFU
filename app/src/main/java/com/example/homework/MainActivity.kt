package com.example.homework

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import com.example.homework.adapter.AuthorAdapter
import com.example.homework.aidl.IMusicAidlInterface
import com.example.homework.service.MusicService
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var musicService : IMusicAidlInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bindService(
            Intent(applicationContext, MusicService::class.java),
            object : ServiceConnection {
                override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                    musicService = IMusicAidlInterface.Stub.asInterface(service)

                    authors.adapter = AuthorAdapter(
                        musicService
                    ) {

                    }
                }

                override fun onServiceDisconnected(name: ComponentName?) {
                    Log.i("pidar","pidar")
                }

            },
            Context.BIND_AUTO_CREATE
        )


    }
}