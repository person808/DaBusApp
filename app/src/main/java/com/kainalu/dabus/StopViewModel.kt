package com.kainalu.dabus

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.kainalu.dabus.dagger.Injector

class StopViewModel : ViewModel() {

    private val stopRepository: StopRepository = Injector.get().stopRepository()

    fun getStopData(): LiveData<List<Stop>> = stopRepository.stopData

    fun getStopData(id: String): LiveData<Stop> = stopRepository.getStop(id)

    fun getStopRealtimeArrivals(id: String): LiveData<List<Arrival>> =
            stopRepository.getRealtimeArrivals(id)

    fun getStopScheduledArrivals(id: String): LiveData<List<Arrival>> =
            stopRepository.getScheduledArrivals(id)
}