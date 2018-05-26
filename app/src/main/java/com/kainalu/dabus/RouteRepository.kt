package com.kainalu.dabus

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class RouteRepository @Inject constructor(private val daBusApi: DaBusApi) {

    val routeData: LiveData<List<Route>>
        get() {
            val routeData = MutableLiveData<List<Route>>()
            daBusApi.routes.enqueue(object : Callback<List<Route>> {
                override fun onResponse(call: Call<List<Route>>, response: Response<List<Route>>) {
                    routeData.value = response.body()
                }

                override fun onFailure(call: Call<List<Route>>, t: Throwable) {
                    Log.e("", "Error getting routes RETROFIT")
                    Log.e("", t.message)
                }
            })
            return routeData
        }

    fun getRouteShape(routeId: String): LiveData<List<ShapePoint>> {
        val routeShape = MutableLiveData<List<ShapePoint>>()
        daBusApi.getRouteShape(routeId).enqueue(object : Callback<List<ShapePoint>> {
            override fun onResponse(call: Call<List<ShapePoint>>, response: Response<List<ShapePoint>>) {
                routeShape.value = response.body()
            }

            override fun onFailure(call: Call<List<ShapePoint>>, t: Throwable) {
                Log.e("", "Error getting route shape RETROFIT")
                Log.e("", t.message)
            }
        })
        return routeShape
    }

    fun getRouteStops(routeId: String): LiveData<List<Stop>> {
        val routeStops = MutableLiveData<List<Stop>>()
        daBusApi.getRouteStops(routeId).enqueue(object : Callback<List<Stop>> {
            override fun onResponse(call: Call<List<Stop>>, response: Response<List<Stop>>) {
                routeStops.value = response.body()
            }

            override fun onFailure(call: Call<List<Stop>>, t: Throwable) {
                Log.e("", "Error getting route shape RETROFIT")
                Log.e("", t.message)
            }
        })
        return routeStops
    }
}