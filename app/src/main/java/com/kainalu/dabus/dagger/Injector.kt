package com.kainalu.dabus.dagger

import com.kainalu.dabus.DaBusApplication

class Injector {
    companion object {
        fun get(): SingletonComponent = DaBusApplication.INSTANCE.component
    }
}