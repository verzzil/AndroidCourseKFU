package com.example.homework

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SeekBar
import com.example.homework.adapters.MusicAdapter
import com.example.homework.data.MusicRepository
import com.example.homework.models.Music
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private var musicAdapter: MusicAdapter? = null
    private var mediaPlayer: MediaPlayer? = null
    private var musicRepository: MusicRepository = MusicRepository
    private var seekBar: SeekBar? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        seekBar = seek_bar

        musicAdapter = MusicAdapter(
            musicRepository.music
        ) { music ->
            trackLaunch(music)
            musicRepository.currentIndex = music.id
            changeBottomData(music)
        }

        rv_music.adapter = musicAdapter

        play_pause.setOnClickListener {
            if (mediaPlayer?.isPlaying == true) {
                play_pause.setImageResource(R.drawable.ic_play)
                mediaPlayer?.pause()
            } else {
                play_pause.setImageResource(R.drawable.ic_baseline_pause_24)
                mediaPlayer?.start()
            }
        }
        skip_to_prev.setOnClickListener {
            if (seekBar?.progress!! > 2000) {
                seekBar?.progress = 0
                mediaPlayer?.seekTo(0)
            } else {
                val prevTrack = MusicRepository.getPrev()
                trackLaunch(prevTrack)
                changeBottomData(prevTrack)
            }

        }
        skip_to_next.setOnClickListener {
            val nextTrack = MusicRepository.getNext()
            trackLaunch(nextTrack)
            changeBottomData(nextTrack)
        }

    }

    private fun changeBottomData(music: Music) {
        play_pause.setImageResource(R.drawable.ic_baseline_pause_24)
        music_title.text = music.trackName
        music_author.text = music.author

        seekBar?.max = mediaPlayer?.duration ?: 0

        Timer().scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                seekBar?.progress = mediaPlayer?.currentPosition ?: 0
            }
        }, 0, 1000)

        seekBar?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser)
                    mediaPlayer?.seekTo(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                Log.i("update", "update")
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                Log.i("update", "update")
            }
        })
    }

    private fun trackLaunch(music: Music) {
        if (mediaPlayer?.isPlaying == true) {
            mediaPlayer?.stop()
            mediaPlayer?.release()
            mediaPlayer = null
        }

        mediaPlayer = MediaPlayer.create(applicationContext, music.musicTrack)
        mediaPlayer?.start()
    }
}