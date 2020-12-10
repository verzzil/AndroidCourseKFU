package com.example.homework.service

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import com.example.homework.aidl.IMusicAidlInterface
import com.example.homework.repository.AuthorMusicRepository

class MusicService : Service() {
    private lateinit var mediaPlayer : MediaPlayer
    private val authorMusicRepository = AuthorMusicRepository
    private var currentAuthor = authorMusicRepository.currentAuthor
    private var currentMusic = authorMusicRepository.currentMusic

    override fun onCreate() {
        mediaPlayer = MediaPlayer.create(applicationContext, authorMusicRepository.getCurrentMusic().musicSrc)
    }

    private val binder = object : IMusicAidlInterface.Stub() {
        override fun play() {
            prepareToPlay(currentAuthor, currentMusic)
            mediaPlayer.start()
        }

        override fun playNewAuthor(authorId: Int, musicId: Int) {
            prepareToPlay(authorId, musicId)
            mediaPlayer.start()
        }

        override fun skipToNext() {
            val nextTrack = authorMusicRepository.getNext()
            prepareToPlay(currentAuthor, nextTrack.id)
            mediaPlayer.start()
        }

        override fun skipToPrev() {
            val nextTrack = authorMusicRepository.getPrev()
            prepareToPlay(currentAuthor, nextTrack.id)
            mediaPlayer.start()
        }

        override fun seekTo(postition: Int) =
            mediaPlayer.seekTo(postition)

        override fun getDuration(): Int =
            mediaPlayer.duration

        override fun getCurrentPostition(): Int =
            mediaPlayer.currentPosition

        override fun prepareToPlay(authorId: Int, musicId: Int) {
            if(currentAuthor != authorId) {
                currentAuthor = authorId
                currentMusic = musicId

                authorMusicRepository.currentAuthor = authorId
                authorMusicRepository.currentMusic = musicId

                mediaPlayer.stop()
                mediaPlayer.release()
                mediaPlayer = MediaPlayer.create(applicationContext, authorMusicRepository.getCurrentMusic().musicSrc)
            } else {
                if(musicId != currentMusic) {
                    currentMusic = musicId

                    mediaPlayer.stop()
                    mediaPlayer.release()
                    mediaPlayer = MediaPlayer.create(applicationContext, authorMusicRepository.getCurrentMusic().musicSrc)
                }
            }
        }

    }

    override fun onBind(intent: Intent?): IBinder? {
        return binder
    }


}