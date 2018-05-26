package com.kainalu.dabus

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.kainalu.dabus.dagger.Injector


class ArrivalViewModel : ViewModel() {

    private val shapeRepository = Injector.get().shapeRepository()

    fun getTripShapeCoords(shapeId: String): LiveData<List<LatLng>> {
        val data = MutableLiveData<List<LatLng>>()
        return Transformations.switchMap(shapeRepository.getShapeData(shapeId)) { shapePoints ->
            val coords = mutableListOf<LatLng>()
            if (shapePoints != null) {
                for ((_, _, _, latitude, longitude) in shapePoints) {
                    coords.add(LatLng(latitude, longitude))
                }
            }
            data.value = coords
            data
        }
    }
}