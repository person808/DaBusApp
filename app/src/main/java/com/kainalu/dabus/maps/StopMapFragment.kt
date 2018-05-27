package com.kainalu.dabus.maps

import android.view.LayoutInflater
import android.view.View
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.kainalu.dabus.R
import com.kainalu.dabus.Stop
import kotlinx.android.synthetic.main.fragment_map.*
import kotlinx.android.synthetic.main.stop_info_window.view.*


abstract class StopMapFragment : MapFragment() {

    protected fun addStopMarker(map: GoogleMap, stop: Stop): Marker {
        val markerOptions = MarkerOptions()
            .position(stop.latLng)
            .title("Stop " + stop.id)
            .snippet(stop.name)
        val marker = map.addMarker(markerOptions)
        marker.tag = stop
        return marker
    }

    protected fun addStopMarkers(map: GoogleMap, stopList: List<Stop>) {
        for (stop in stopList) {
            addStopMarker(map, stop)
        }
        swipeRefreshLayout.isRefreshing = false
    }

    override fun onMapReady(googleMap: GoogleMap) {
        super.onMapReady(googleMap)
        map.setInfoWindowAdapter(object : GoogleMap.InfoWindowAdapter {
            override fun getInfoWindow(marker: Marker): View? {
                return null
            }

            override fun getInfoContents(marker: Marker): View {
                val view = LayoutInflater.from(context!!).inflate(R.layout.stop_info_window, null)
                view.titleTextView.text = marker.title
                view.detailTextView.text = marker.snippet
                return view
            }
        })
    }
}