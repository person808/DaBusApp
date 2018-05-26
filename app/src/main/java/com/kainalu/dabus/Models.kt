package com.kainalu.dabus

import com.google.android.gms.maps.model.LatLng
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class Arrival(
    val id: Int,
    val trip: Int,
    val route: String,
    val headsign: String,
    val vehicle: String?,
    val direction: String?,
    val stopTime: String?,
    val date: String?,
    val estimated: Int,
    val longitude: Double?,
    val latitude: Double?,
    val shape: String,
    val canceled: Int
)

@JsonClass(generateAdapter = true)
data class Route(
    @Json(name = "agency_id")
    val agencyId: Int,
    @Json(name = "feed_id")
    val feedId: String,
    @Json(name = "route_color")
    val color: String?,
    @Json(name = "route_desc")
    val desc: String?,
    @Json(name = "route_id")
    val id: String,
    @Json(name = "route_long_name")
    val longName: String,
    @Json(name = "route_short_name")
    val shortName: String,
    @Json(name = "route_text_color")
    val textColor: String?,
    @Json(name = "route_type")
    val type: Int,
    @Json(name = "route_url")
    val url: String?
)

@JsonClass(generateAdapter = true)
data class ShapePoint (
    @Json(name = "feed_id")
    val feedId: String,
    @Json(name = "shape_dist_traveled")
    val shapeDistTraveled: Double,
    @Json(name = "shape_id")
    val shapeId: String,
    @Json(name = "shape_pt_lat")
    val latitude: Double,
    @Json(name = "shape_pt_lon")
    val longitude: Double,
    @Json(name = "shape_pt_sequence")
    val sequence: Int
)

@JsonClass(generateAdapter = true)
data class Stop(
        @Json(name = "feed_id")
        val feedId: String,
        @Json(name = "location_type")
        val locationType: Int,
        @Json(name = "parent_station")
        val parentStation: String?,
        @Json(name = "stop_code")
        val code: String? = null,
        @Json(name = "stop_desc")
        val description: String?,
        @Json(name = "stop_id")
        val id: String? = null,
        @Json(name = "stop_lat")
        val lat: Double,
        @Json(name = "stop_lon")
        val lon: Double,
        @Json(name = "stop_name")
        val name: String,
        @Json(name = "stop_timezone")
        val timezone: String?,
        @Json(name = "stop_url")
        val url: String,
        @Json(name = "wheelchair_boarding")
        val wheelchairBoarding: Int,
        @Json(name = "zone_id")
        val zoneId: String? = null) {

    val latLng: LatLng
        get() = LatLng(this.lat, this.lon)
}
