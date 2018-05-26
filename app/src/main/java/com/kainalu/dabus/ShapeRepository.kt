package com.kainalu.dabus

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


class ShapeRepository @Inject constructor(private val daBusApi: DaBusApi) {

    fun getShapeData(shapeID: String): LiveData<List<ShapePoint>> {
        val shape = MutableLiveData<List<ShapePoint>>()
        daBusApi.getShape(shapeID).enqueue(object : Callback<List<ShapePoint>> {
            override fun onResponse(call: Call<List<ShapePoint>>, response: Response<List<ShapePoint>>) {
                shape.value = response.body()
            }

            override fun onFailure(call: Call<List<ShapePoint>>, t: Throwable) {
                Log.e("", "Error getting shape RETROFIT")
                Log.e("", t.message)
            }
        })
        return shape
    }
}
