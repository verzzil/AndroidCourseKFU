package com.example.homework

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.SeekBar
import com.example.homework.adapter.AuthorAdapter
import com.example.homework.aidl.IMusicAidlInterface
import com.example.homework.repository.AuthorMusicRepository
import com.example.homework.service.MusicService
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var musicService : IMusicAidlInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        play_pause.tag = R.drawable.ic_play

        bindService(
            Intent(applicationContext, MusicService::class.java),
            object : ServiceConnection {
                override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                    musicService = IMusicAidlInterface.Stub.asInterface(service)

                    authors.adapter = AuthorAdapter(
                        musicService,
                        this@MainActivity
                    ) {
                        if(it.findViewById<View>(R.id.musics).visibility == View.GONE)
                            it.findViewById<View>(R.id.musics).visibility = View.VISIBLE
                        else
                            it.findViewById<View>(R.id.musics).visibility = View.GONE
                    }

                    if(musicService.isPlaying) {
                        play_pause.tag = R.drawable.ic_pause
                        play_pause.setImageResource(R.drawable.ic_pause)
                    }

                    prev.setOnClickListener {
                        musicService.skipToPrev()
                        changeTitles()
                    }
                    next.setOnClickListener {
                        musicService.skipToNext()
                        changeTitles()
                    }
                    play_pause.setOnClickListener {
                        if (play_pause.tag == R.drawable.ic_play) {
                            play_pause.tag = R.drawable.ic_pause
                            play_pause.setImageResource(R.drawable.ic_pause)

                            musicService.play()
                        } else {
                            play_pause.tag = R.drawable.ic_play
                            play_pause.setImageResource(R.drawable.ic_play)

                            musicService.pause()
                        }
                    }

                    seek_bar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                        override fun onProgressChanged(
                            seekBar: SeekBar?,
                            progress: Int,
                            fromUser: Boolean
                        ) {
                            if(fromUser) {
                                musicService.seekTo(progress)
                            }
                        }

                        override fun onStartTrackingTouch(seekBar: SeekBar?) {

                        }

                        override fun onStopTrackingTouch(seekBar: SeekBar?) {

                        }

                    })
                    Timer().scheduleAtFixedRate(object : TimerTask() {
                        override fun run() {
                            seek_bar.progress = musicService.currentPostition
                            if (seek_bar.progress > (seek_bar.max - 1000) && seek_bar.progress != 0) {
                                musicService.skipToNext()
                                runOnUiThread { changeTitles() }
                            }
                        }
                    }, 0, 1000)
                }

                override fun onServiceDisconnected(name: ComponentName?) {
                    Log.i("disconnect","disconnect")
                }

            },
            Context.BIND_AUTO_CREATE
        )


    }

    fun changeTitles() {
        AuthorMusicRepository.also {
            track_author.text = it.getCurrentAuthorName()
            track_title.text = it.getCurrentMusicTitle()
        }
        play_pause.setImageResource(R.drawable.ic_pause)
        play_pause.tag = R.drawable.ic_pause
        seek_bar.max = musicService.duration
        seek_bar.progress = 0
    }
}