package com.kainalu.dabus

import android.app.Application
import com.kainalu.dabus.dagger.ContextModule
import com.kainalu.dabus.dagger.DaggerSingletonComponent
import com.kainalu.dabus.dagger.ServiceModule
import com.kainalu.dabus.dagger.SingletonComponent

class DaBusApplication : Application() {

    lateinit var component: SingletonComponent
        private set

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        component = DaggerSingletonComponent.builder()
            .contextModule(ContextModule(this))
            .serviceModule(ServiceModule())
            .build()
    }

    companion object {
        lateinit var INSTANCE: DaBusApplication
            private set
    }
}