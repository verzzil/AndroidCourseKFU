package com.example.homework

import android.app.ActivityManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.media.MediaPlayer
import android.os.Bundle
import android.os.IBinder
import android.os.RemoteException
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.util.Log
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.example.homework.adapters.MusicAdapter
import com.example.homework.data.MusicRepository
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity() {

    private var musicAdapter: MusicAdapter? = null
    private var mediaPlayer: MediaPlayer? = null
    private var musicRepository: MusicRepository = MusicRepository
    private var seekBar: SeekBar? = null

    var musicServiceBinder: MusicService.MusicServiceBinder? = null
    var mediaController: MediaControllerCompat? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        seekBar = seek_bar
        musicAdapter = MusicAdapter(
            MusicRepository.music
        ) {
            if (musicRepository.currentIndex == it.id) {
                if (play_pause.tag == R.drawable.ic_play) {
                    mediaController?.transportControls?.play()
                } else {
                    mediaController?.transportControls?.pause()
                }
            } else {
                musicRepository.currentIndex = it.id
                mediaController?.transportControls?.play()
            }
        }
        play_pause.tag = R.drawable.ic_play

        rv_music.adapter = musicAdapter


        bindService(
            Intent(
                this,
                MusicService::class.java
            ),
            object : ServiceConnection {
                override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                    musicServiceBinder = service as MusicService.MusicServiceBinder


                    musicServiceBinder?.isBinderAlive

                    try {
                        mediaController = musicServiceBinder?.getMediaSessionToken()?.let {
                            MediaControllerCompat(
                                applicationContext,
                                it
                            )
                        }
                        mediaController?.registerCallback(
                            object : MediaControllerCompat.Callback() {
                                override fun onPlaybackStateChanged(state: PlaybackStateCompat?) {
                                    if (state == null) {
                                        return
                                    }
                                    var playing = state.state == PlaybackStateCompat.STATE_PLAYING
                                    var skipNext =
                                        state.state == PlaybackStateCompat.STATE_SKIPPING_TO_NEXT
                                    var skipPrev =
                                        state.state == PlaybackStateCompat.STATE_SKIPPING_TO_PREVIOUS
                                    var pause = state.state == PlaybackStateCompat.STATE_PAUSED

                                    val currentTrack = musicRepository.getCurrentTrack()

                                    if (pause) {
                                        play_pause.setImageResource(R.drawable.ic_play)
                                        play_pause.tag = R.drawable.ic_play
                                    }

                                    if (skipNext || skipPrev || playing) {
                                        play_pause.setImageResource(R.drawable.ic_baseline_pause_24)
                                        play_pause.tag = R.drawable.ic_baseline_pause_24
                                        music_title.text = currentTrack.trackName
                                        music_author.text = currentTrack.author
                                    }

                                }

                                override fun onMetadataChanged(metadata: MediaMetadataCompat?) {
                                    if (metadata == null)
                                        return

                                    music_title.text = metadata.bundle.getString(MediaMetadataCompat.METADATA_KEY_TITLE)
                                    music_author.text = metadata.bundle.getString(MediaMetadataCompat.METADATA_KEY_ALBUM)

                                    seekBar?.max =
                                        metadata.bundle.getLong(MediaMetadataCompat.METADATA_KEY_DURATION)
                                            .toInt()

                                    Timer().scheduleAtFixedRate(object : TimerTask() {
                                        override fun run() {
                                            seekBar?.progress =
                                                musicServiceBinder?.getCurrentPosition() ?: 0
                                        }
                                    }, 0, 1000)

                                    seekBar?.setOnSeekBarChangeListener(object :
                                        SeekBar.OnSeekBarChangeListener {
                                        override fun onProgressChanged(
                                            seekBar: SeekBar?,
                                            progress: Int,
                                            fromUser: Boolean
                                        ) {
                                            if (fromUser)
                                                mediaController?.transportControls?.seekTo(progress.toLong())
                                            if ((seekBar?.max?.minus(1000))!! <= progress)
                                                mediaController?.transportControls?.skipToNext()

                                            Log.i("mediaController", "${mediaController}")

                                        }

                                        override fun onStartTrackingTouch(seekBar: SeekBar?) {
                                            Log.i("update", "update")
                                        }

                                        override fun onStopTrackingTouch(seekBar: SeekBar?) {
                                            Log.i("update", "update")
                                        }
                                    })
                                }
                            }
                        )
                    } catch (e: RemoteException) {
                        mediaController = null
                    }

                }

                override fun onServiceDisconnected(name: ComponentName?) {
                    musicServiceBinder = null
                    mediaController = null
                }
            },
            Context.BIND_AUTO_CREATE
        )

        play_pause.setOnClickListener {
            if (mediaController != null) {
                if (play_pause.tag == R.drawable.ic_play) {
                    mediaController?.transportControls?.play()
                } else {
                    mediaController?.transportControls?.pause()
                }
            }
        }

        skip_to_next.setOnClickListener {
            mediaController?.transportControls?.skipToNext()
        }
        skip_to_prev.setOnClickListener {
            mediaController?.transportControls?.skipToPrevious()
        }



    }

    private fun isMyServiceRunning(serviceClass: Class<*>): Boolean {
        val manager =
            getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Int.MAX_VALUE)) {
            if (serviceClass.name == service.service.className) {
                return true
            }
        }
        return false
    }

}