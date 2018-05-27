package com.kainalu.dabus

import android.os.Bundle
import com.kainalu.dabus.DaBusApplication.Companion.EXTRA_STOP_ID
import com.kainalu.dabus.arrivals.ArrivalFragment


/**
 * Activity to show detailed information like arrivals at a stop,
 * a route map, etc.
 */
class InfoActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_placeholder)

        val id = intent.getStringExtra(EXTRA_STOP_ID)

        fragment = if (savedInstanceState != null) {
            supportFragmentManager.getFragment(savedInstanceState, CURRENT_FRAGMENT)
        } else {
            ArrivalFragment.newInstance(id)
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.placeholder, fragment, CURRENT_FRAGMENT)
            .commit()
    }
}
