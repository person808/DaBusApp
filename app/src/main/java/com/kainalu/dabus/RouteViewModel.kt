package com.kainalu.dabus

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.kainalu.dabus.dagger.Injector

class RouteViewModel : ViewModel() {

    private val routeRepository = Injector.get().routeRepository()
    private lateinit var routeData: LiveData<List<Route>>

    fun getRouteData(refresh: Boolean = false): LiveData<List<Route>> {
        if (!::routeData.isInitialized || refresh) {
            routeData = routeRepository.routeData
        }
        return routeData
    }

    fun getRouteShape(id: String) = routeRepository.getRouteShape(id)

    fun getRouteStops(id: String) = routeRepository.getRouteStops(id)
}