package com.kainalu.dabus.dagger

import com.kainalu.dabus.DaBusApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
class ServiceModule {

    @Provides
    @Singleton
    fun retrofit(): Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun daBusService(retrofit: Retrofit): DaBusApi =
            retrofit.create(DaBusApi::class.java)

    companion object {
        const val BASE_URL = "http://159.89.157.81"
    }
}