package com.kainalu.dabus.dagger

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ContextModule(private val application: Application) {

    @Provides
    @Singleton
    fun appContext(): Context = application

    @Provides
    @Singleton
    fun application(): Application = application
}