package com.kainalu.dabus

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
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
        AndroidThreeTen.init(this)
    }

    companion object {
        lateinit var INSTANCE: DaBusApplication
            private set
        internal const val EXTRA_STOP_ID = "com.kainalu.dabus.EXTRA_STOP_ID"
    }
}