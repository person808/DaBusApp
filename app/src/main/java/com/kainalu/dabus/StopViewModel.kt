package com.kainalu.dabus

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import com.kainalu.dabus.dagger.Injector

class StopViewModel : ViewModel() {

    private val stopRepository: StopRepository = Injector.get().stopRepository()
    private lateinit var stopData: LiveData<List<Stop>>

    fun getStopData(refresh: Boolean = false): LiveData<List<Stop>> {
        if (!::stopData.isInitialized || refresh) {
            stopData = stopRepository.stopData
        }
        return stopData
    }

    fun getStopData(id: String): LiveData<Stop> = stopRepository.getStop(id)

    fun getFavoriteStops(): LiveData<List<Stop>> {
        return Transformations.switchMap(getStopData(), {
            val data = MutableLiveData<List<Stop>>()
            data.value = it.filter { stopRepository.stopInFavorites(it.id) }
            data
        })
    }

    fun addFavoriteStop(stopId: String) = stopRepository.addFavoriteStop(stopId)

    fun removeFavoriteStop(stopId: String) = stopRepository.removeFavoriteStop(stopId)

    fun stopInFavorites(stopId: String): Boolean = stopRepository.stopInFavorites(stopId)

    fun getStopRealtimeArrivals(id: String): LiveData<List<Arrival>> =
            stopRepository.getRealtimeArrivals(id)

    fun getStopScheduledArrivals(id: String): LiveData<List<Arrival>> =
            stopRepository.getScheduledArrivals(id)
}