package com.example.myweather

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.os.SystemClock
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModelProviders
import com.example.myweather.logic.Repository
import com.example.myweather.logic.model.Location
import com.example.myweather.ui.weather.WeatherActivity
import com.example.myweather.ui.weather.WeatherViewModel
import kotlin.concurrent.thread
import kotlin.reflect.KProperty

object  MyService : Service() {
    var locationLng = ""
    var locationLat = ""

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
            updateWeather(locationLng,locationLat)
            val manger:AlarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val anHour = 8*60*60*1000
            val triggerAtTime = SystemClock.elapsedRealtime()+anHour
            val i = Intent(this, MyService::class.java)
            val pi = PendingIntent.getService(this,0,i,0)
            manger.cancel(pi)
            manger.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggerAtTime,pi)
            stopSelf()
        return super.onStartCommand(intent, flags, startId)
    }

    fun updateWeather(lng:String,lat:String){
        val locationLiveData = MutableLiveData<Location>()
        locationLiveData.value = Location(lng,lat)
    }

}



