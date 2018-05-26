package com.kainalu.dabus


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Defines the interface to retrieve API data.
 */

interface DaBusApi {

    @get:GET("/thebus/api/v1.0/stops")
    val stops: Call<List<Stop>>

    @get:GET("/thebus/api/v1.0/routes")
    val routes: Call<List<Route>>

    @GET("/thebus/api/v1.0/arrivals/realtime/{stop}")
    fun getRealtimeArrivals(@Path("stop") stopId: String): Call<List<Arrival>>

    @GET("/thebus/api/v1.0/arrivals/scheduled/{stop}")
    fun getScheduledArrivals(@Path("stop") stopId: String): Call<List<Arrival>>

    @GET("/thebus/api/v1.0/stops/{id}")
    fun getStop(@Path("id") id: String): Call<Stop>

    @GET("/thebus/api/v1.0/shapes/{shapeId}")
    fun getShape(@Path("shapeId") shapeId: String): Call<List<ShapePoint>>

    @GET("/thebus/api/v1.0/routes/{routeId}/shape")
    fun getRouteShape(@Path("routeId") routeId: String): Call<List<ShapePoint>>

    @GET("/thebus/api/v1.0/routes/{routeId}/stops")
    fun getRouteStops(@Path("routeId") routeId: String): Call<List<Stop>>
}
