package com.example.madprac_4

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.TextClock
import android.widget.TimePicker
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.text.DateFormat
import java.text.SimpleDateFormat

class MainActivity : AppCompatActivity() {
    lateinit var clock: TextClock
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets

        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (checkSelfPermission(android.Manifest.permission.SCHEDULE_EXACT_ALARM) != PackageManager.PERMISSION_GRANTED) {
                val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
                startActivity(intent)
            }
        }
        findViewById<Button>(R.id.play).setOnClickListener(){
            Intent(applicationContext,MyService::class.java).
                    putExtra("Service1","PlayPause").apply {
                        startService(this)
            }
        }
        findViewById<Button>(R.id.stop).setOnClickListener(){
            Intent(applicationContext,MyService::class.java).
            putExtra("Service1","Stopn").apply {
                startService(this)
            }
        }
        clock = findViewById(R.id.Clock)
        clock.format12Hour = "hh:mm:ss a dd:MMM:yyyy"

        findViewById<Button>(R.id.alarm1).setOnClickListener {
            showTimerDialog()
        }
    }
    private fun sendDialogDataToActivity(hour: Int,min: Int){
        val millis = getMillis(hour, min)
        setAlarm(millis, "Start")
    }
    fun getCurrentDateTime():String{
        val cal = Calendar.getInstance()
        val df : DateFormat = SimpleDateFormat("MMM, dd yyyy hh:mm:ss")
        return df.format(cal.time)
    }
    fun getMillis(hour:Int, min:Int):Long{
        val setCalender = Calendar.getInstance()
        setCalender[Calendar.HOUR_OF_DAY] = hour
        setCalender[Calendar.MINUTE] = min
        setCalender[Calendar.SECOND] = 0
        return setCalender.timeInMillis
    }
    fun showTimerDialog(){
        val cldr : Calendar = Calendar.getInstance()
        val hour: Int = cldr.get(Calendar.HOUR)
        val min: Int = cldr.get(Calendar.MINUTE)
        val picker = TimePickerDialog(
            this,
            {_, sHour, sMinute -> sendDialogDataToActivity(sHour, sMinute)},
            hour,
            min,
            false
        )
        picker.show()
    }
    fun setAlarm(millis:Long, str:String){
        val intent = Intent(this, AlarmBroadcastReceiver::class.java)
        intent.putExtra("Service1", str)
        val penIntent = PendingIntent.getBroadcast(applicationContext,534,intent, PendingIntent.FLAG_IMMUTABLE)
        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, millis, penIntent)

    }
}