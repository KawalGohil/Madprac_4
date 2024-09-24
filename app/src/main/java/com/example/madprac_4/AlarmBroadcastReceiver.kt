package com.example.madprac_4

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class AlarmBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.getStringExtra("Service1")
        if (action == "Start") {
            Toast.makeText(context, "Alarm Triggered!", Toast.LENGTH_SHORT).show()

            // Optionally, start MyService if you want to play a sound or do something when the alarm triggers
            val serviceIntent = Intent(context, MyService::class.java)
            serviceIntent.putExtra("Service1", "PlayPause")
            context.startService(serviceIntent)
        }
    }
}