package com.example.myweather

import android.app.AlarmManager
import android.app.IntentService
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.os.SystemClock
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.myweather.logic.model.Location
import com.example.myweather.ui.weather.WeatherActivity


class MyService : IntentService("Myservice") {
    companion object{
        var locationLng = ""
        var locationLat = ""
    }
    override fun onHandleIntent(intent: Intent?) {
        Log.d("MyIntentService","Thread id is ${Thread.currentThread().name}}")

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        updateWeather(locationLng,locationLat)
        val manger:AlarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val anHour = 20*1000
        val triggerAtTime = SystemClock.elapsedRealtime()+anHour
        val i = Intent(this, MyService::class.java)
        val pi = PendingIntent.getService(this,0,i,0)
        manger.cancel(pi)
        manger.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggerAtTime,pi)
        return super.onStartCommand(intent, flags, startId)
    }

    fun updateWeather(lng:String,lat:String){
        val locationLiveData = MutableLiveData<Location>()
        locationLiveData.value = Location(lng,lat)
    }

}



