package com.example.homework

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.SeekBar
import com.example.homework.service.MyMusicService
import kotlinx.android.synthetic.main.activity_main2.*

class MainActivity2 : AppCompatActivity() {

    lateinit var musicServiceBinder : MyMusicService.MyMusicIBinder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        bindService(
            Intent(this, MyMusicService::class.java).also {
                it.action = "Start"
            },
            object : ServiceConnection {
                override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                    musicServiceBinder = service as MyMusicService.MyMusicIBinder

                    musicServiceBinder.updateSeekBar(seekbar)

                    seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                        override fun onProgressChanged(
                            seekBar: SeekBar?,
                            progress: Int,
                            fromUser: Boolean
                        ) {
                            if(fromUser)
                                musicServiceBinder.seekTo(progress)
                        }

                        override fun onStartTrackingTouch(seekBar: SeekBar?) {
                            Log.i("plug","plug")
                        }

                        override fun onStopTrackingTouch(seekBar: SeekBar?) {
                            Log.i("plug","plug")
                        }

                    })
                    prev.setOnClickListener {
                        musicServiceBinder.skipToPrev()
                        musicServiceBinder.updateSeekBar(seekbar)
                    }
                    play.setOnClickListener {
                        musicServiceBinder.play(seekbar)
                    }
                    pause.setOnClickListener {
                        musicServiceBinder.pause()
                    }
                    next.setOnClickListener {
                        musicServiceBinder.skipToNext()
                        musicServiceBinder.updateSeekBar(seekbar)
                    }

                }

                override fun onServiceDisconnected(name: ComponentName?) {

                }

            },
            Context.BIND_AUTO_CREATE)
    }

}