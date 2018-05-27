package com.kainalu.dabus.maps

import android.Manifest
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.kainalu.dabus.DaBusApplication.Companion.EXTRA_STOP_ID
import com.kainalu.dabus.InfoActivity
import com.kainalu.dabus.Stop
import com.kainalu.dabus.StopViewModel
import com.kainalu.dabus.dagger.Injector
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.OnNeverAskAgain
import permissions.dispatcher.OnPermissionDenied
import permissions.dispatcher.RuntimePermissions


@RuntimePermissions
class NearbyStopMapFragment : StopMapFragment(), OnMapReadyCallback,
    GoogleMap.OnInfoWindowClickListener,
    GoogleMap.OnMarkerClickListener, GoogleMap.OnCameraIdleListener {

    private lateinit var stopViewModel: StopViewModel
    private var stopList: List<Stop>? = null
    private val fusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(Injector.get().appContext())
    private val locationRequest = LocationRequest.create()
        .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)
    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult?) {
            result?.run {
                val latLng = lastLocation.let { LatLng(it.latitude, it.longitude) }
                map.moveCamera(CameraUpdateFactory.newLatLng(latLng))
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        stopViewModel = ViewModelProviders.of(activity!!)[StopViewModel::class.java]
    }

    override fun onPause() {
        super.onPause()
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

    @NeedsPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    fun startLocationUpdates() {
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null)
        map.isMyLocationEnabled = true
    }

    @OnPermissionDenied(Manifest.permission.ACCESS_FINE_LOCATION)
    @OnNeverAskAgain(Manifest.permission.ACCESS_FINE_LOCATION)
    fun moveToDefaultLocation() {
        // Camera location is saved on configuration change so we don't
        // need to move the camera if it is non-null.
        if (camera == null) {
            val latLng = Injector.get().defaultLocation().let {
                LatLng(it.latitude, it.longitude)
            }
            map.moveCamera(CameraUpdateFactory.newLatLng(latLng))
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<out String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult(requestCode, grantResults)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        super.onMapReady(googleMap)

        startLocationUpdatesWithPermissionCheck()
        map.setOnCameraIdleListener(this)
        map.setOnMarkerClickListener(this)
        map.setOnInfoWindowClickListener(this)

        stopViewModel.getStopData().observe(this, Observer {
            it?.let {
                stopList = it
                addStopMarkers(map, filterStops(stopList))
            }
        })
    }

    override fun onCameraIdle() {
        if (stopList != null) {
            addStopMarkers(map, filterStops(stopList))
        }
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        map.animateCamera(CameraUpdateFactory.newLatLng(marker.position))
        marker.showInfoWindow()
        return true
    }

    override fun onInfoWindowClick(marker: Marker?) {
        marker?.also {
            val intent = Intent(context, InfoActivity::class.java).apply {
                putExtra(EXTRA_STOP_ID, (it.tag as Stop).id)
            }
            startActivity(intent)
        }
    }

    private fun filterStops(stopList: List<Stop>?): List<Stop> {
        val filteredList = mutableListOf<Stop>()
        val bounds = map.projection.visibleRegion.latLngBounds
        for (stop in stopList!!) {
            if (bounds.contains(stop.latLng)) {
                filteredList.add(stop)
            }
        }
        return filteredList
    }
}
