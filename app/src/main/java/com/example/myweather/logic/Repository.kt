package com.example.myweather.logic

import androidx.lifecycle.liveData
import com.example.myweather.logic.model.Place
import com.example.myweather.logic.network.MyWeatherNetwork

import kotlinx.coroutines.Dispatchers
import java.lang.Exception

object Repository {

    fun searchPlaces(query:String) = liveData(Dispatchers.IO){
        val result = try{
            val placeResponse = MyWeatherNetwork.searchPlaces(query)
            if(placeResponse.status=="ok"){
                val places = placeResponse.places
                Result.success(places)
            }else{
                Result.failure(RuntimeException("response status is ${placeResponse.status}"))
            }
        }catch (e:Exception){
            Result.failure<List<Place>>(e)
        }
        emit(result as Result<List<Place>>)
    }

}