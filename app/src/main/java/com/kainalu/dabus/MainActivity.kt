package com.kainalu.dabus

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.view.MenuItem
import com.kainalu.dabus.maps.NearbyStopMapFragment
import com.kainalu.dabus.search.SearchTypesFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private val nearbyStopMapFragment = NearbyStopMapFragment()
    private val favoriteStopsFragment = FavoriteStopsFragment()
    private val searchTypesFragment = SearchTypesFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fragment = if (savedInstanceState != null) {
            supportFragmentManager.getFragment(savedInstanceState, CURRENT_FRAGMENT)
        } else {
            nearbyStopMapFragment
        }

        switchFragment(fragment)
        bottomNavigationView.setOnNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nearby -> switchFragment(nearbyStopMapFragment)
            R.id.favorites -> switchFragment(favoriteStopsFragment)
            R.id.search -> switchFragment(searchTypesFragment)
        }
        return true
    }

    private fun switchFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.placeholder, fragment, CURRENT_FRAGMENT)
            .commit()
    }
}
