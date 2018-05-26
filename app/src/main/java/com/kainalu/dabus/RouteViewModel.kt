package com.kainalu.dabus

import android.arch.lifecycle.ViewModel
import com.kainalu.dabus.dagger.Injector

class RouteViewModel : ViewModel() {

    private val routeRepository = Injector.get().routeRepository()

    fun getRouteData() = routeRepository.routeData

    fun getRouteShape(id: String) = routeRepository.getRouteShape(id)

    fun getRouteStops(id: String) = routeRepository.getRouteStops(id)
}