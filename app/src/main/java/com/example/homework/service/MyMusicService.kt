package com.example.homework.service

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.*
import android.util.Log
import androidx.appcompat.widget.AppCompatSeekBar
import com.example.homework.R
import com.example.homework.data.MusicRepository
import kotlinx.android.synthetic.main.activity_main2.*
import java.util.*

class MyMusicService : Service() {

    private lateinit var mediaPlayer: MediaPlayer
    private val musicRepository = MusicRepository

    private var currentMusicSrc: Int = 0

    override fun onBind(intent: Intent): IBinder {

        mediaPlayer =
            MediaPlayer.create(applicationContext, musicRepository.getCurrentTrack().musicTrack)

        when (intent.action) {
            "Start" -> {
                startService(intent)
            }
        }

        return MyMusicIBinder()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i("serviceSay", "onStartCommand")

        return super.onStartCommand(intent, flags, startId)
    }

    private fun prepareToPlay(musicSrc: Int) {
        if (musicSrc != currentMusicSrc) {
            currentMusicSrc = musicSrc
            mediaPlayer.stop()
            mediaPlayer.release()
            mediaPlayer = MediaPlayer.create(applicationContext, musicSrc)
        }
    }

    open inner class MyMusicIBinder() : Binder() {

        fun play(seekBar: AppCompatSeekBar) {
            val currentTrack = musicRepository.getCurrentTrack()
            prepareToPlay(currentTrack.musicTrack)
            mediaPlayer.start()

            Timer().scheduleAtFixedRate(object : TimerTask() {
                override fun run() {
                    mediaPlayer.setOnCompletionListener {
                        skipToNext()
                        updateSeekBar(seekBar)
                    }
                }
            }, 0, 1000)
        }

        fun pause() {
            mediaPlayer.pause()
        }

        fun skipToNext() {
            val nextTrack = musicRepository.getNext()

            prepareToPlay(nextTrack.musicTrack)
            mediaPlayer.start()
        }

        fun skipToPrev() {
            val prevTrack = musicRepository.getPrev()

            prepareToPlay(prevTrack.musicTrack)
            mediaPlayer.start()
        }

        fun seekTo(pos: Int) {
            mediaPlayer.seekTo(pos)
        }

        fun getDuration(): Int = mediaPlayer.duration

        fun getCurrentPosition(): Int = mediaPlayer.currentPosition

        fun updateSeekBar(seekBar: AppCompatSeekBar) {
            seekBar.max = getDuration()
            Timer().scheduleAtFixedRate(object : TimerTask() {
                override fun run() {
                    seekBar.progress = getCurrentPosition()
                }
            }, 0, 1000)
        }
    }
}