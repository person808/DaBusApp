package com.kainalu.dabus

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.kainalu.dabus.maps.NearbyStopMapFragment

class MainActivity : AppCompatActivity() {

    private lateinit var fragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fragment = if (savedInstanceState != null) {
            supportFragmentManager.getFragment(savedInstanceState, CURRENT_FRAGMENT)
        } else {
            NearbyStopMapFragment()
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.placeholder, fragment, CURRENT_FRAGMENT)
            .commit()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        supportFragmentManager.putFragment(outState, CURRENT_FRAGMENT, fragment)
    }

    companion object {
        private const val CURRENT_FRAGMENT = "currentFragment"
    }
}
