package com.example.madprac_4

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.IBinder
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MyService : Service() {
    lateinit var mediaPlayer: MediaPlayer
    override fun onBind(intent: Intent?): IBinder {
        TODO("Return the communication channel to service.")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (!this::mediaPlayer.isInitialized)
            mediaPlayer = MediaPlayer.create(this,R.raw.alarm)
        if(intent!=null) {
            val str1: String? = intent.getStringExtra("Service1")
            when (str1) {
                "PlayPause" -> {
                    if (!mediaPlayer.isPlaying) {
                        mediaPlayer.start()
                    } else {
                        mediaPlayer.pause()
                    }
                }

                "Stop" -> {
                    mediaPlayer.stop()
                    stopSelf()
                }
            }
        }
        return START_STICKY
    }

    override fun onDestroy() {
        if (this::mediaPlayer.isInitialized && mediaPlayer.isPlaying) {
            mediaPlayer.stop()
        }
        super.onDestroy()
    }

}