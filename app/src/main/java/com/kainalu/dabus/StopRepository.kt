package com.kainalu.dabus

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton


/**
 * Retrieves Stop data from API.
 */

@Singleton
class StopRepository @Inject constructor(private val daBusApi: DaBusApi) {

    val stopData: LiveData<List<Stop>>
        get() {
            val stopData = MutableLiveData<List<Stop>>()
            daBusApi.stops.enqueue(object : Callback<List<Stop>> {
                override fun onResponse(call: Call<List<Stop>>, response: Response<List<Stop>>) {
                    stopData.value = response.body()
                }

                override fun onFailure(call: Call<List<Stop>>, t: Throwable) {
                    Log.e("", "Error getting stop RETROFIT")
                    Log.e("", t.message)
                }
            })
            return stopData
        }

    fun getStop(id: String): LiveData<Stop> {
        val stopData = MutableLiveData<Stop>()
        daBusApi.getStop(id).enqueue(object : Callback<Stop> {
            override fun onResponse(call: Call<Stop>, response: Response<Stop>) {
                stopData.value = response.body()
            }

            override fun onFailure(call: Call<Stop>, t: Throwable) {
                Log.e("", "Error getting stop RETROFIT")
                Log.e("", t.message)
            }
        })
        return stopData
    }

    fun getRealtimeArrivals(stopId: String): LiveData<List<Arrival>> {
        val stopArrivals = MutableLiveData<List<Arrival>>()
        daBusApi.getRealtimeArrivals(stopId).enqueue(object : Callback<List<Arrival>> {
            override fun onResponse(call: Call<List<Arrival>>, response: Response<List<Arrival>>) {
                stopArrivals.value = response.body()
            }

            override fun onFailure(call: Call<List<Arrival>>, t: Throwable) {
                Log.e("", "Error getting stopArrivals RETROFIT")
                Log.e("", t.message)
            }
        })
        return stopArrivals
    }

    fun getScheduledArrivals(stopId: String): LiveData<List<Arrival>> {
        val stopArrivals = MutableLiveData<List<Arrival>>()
        daBusApi.getScheduledArrivals(stopId).enqueue(object : Callback<List<Arrival>> {
            override fun onResponse(call: Call<List<Arrival>>, response: Response<List<Arrival>>) {
                stopArrivals.value = response.body()
            }

            override fun onFailure(call: Call<List<Arrival>>, t: Throwable) {
                Log.e("", "Error getting stopArrivals RETROFIT")
                Log.e("", t.message)
            }
        })
        return stopArrivals
    }
}
