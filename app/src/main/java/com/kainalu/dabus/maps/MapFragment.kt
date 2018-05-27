package com.kainalu.dabus.maps

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.kainalu.dabus.R
import kotlinx.android.synthetic.main.fragment_map.*


abstract class MapFragment : Fragment(), OnMapReadyCallback {

    protected lateinit var map: GoogleMap
    protected var camera: CameraPosition? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (savedInstanceState != null) {
            camera = savedInstanceState.getParcelable(CAMERA_POSITION)
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        swipeRefreshLayout.isEnabled = false
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(CAMERA_POSITION, map.cameraPosition)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        if (camera != null) {
            map.moveCamera(CameraUpdateFactory.newCameraPosition(camera))
        }
    }

    companion object {
        private const val CAMERA_POSITION = "cameraPosition"
    }
}