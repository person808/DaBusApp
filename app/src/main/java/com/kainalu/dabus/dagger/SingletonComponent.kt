package com.kainalu.dabus.dagger

import android.content.Context
import android.location.Location
import com.kainalu.dabus.DaBusApi
import com.kainalu.dabus.RouteRepository
import com.kainalu.dabus.ShapeRepository
import com.kainalu.dabus.StopRepository
import dagger.Component
import retrofit2.Retrofit
import javax.inject.Singleton

@Component(modules = [ContextModule::class, ServiceModule::class])
@Singleton
interface SingletonComponent {
    fun appContext(): Context
    fun retrofit(): Retrofit
    fun daBusService(): DaBusApi
    fun routeRepository(): RouteRepository
    fun shapeRepository(): ShapeRepository
    fun stopRepository(): StopRepository
    fun defaultLocation(): Location
}
