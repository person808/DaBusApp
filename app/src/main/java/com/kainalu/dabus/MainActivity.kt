package com.kainalu.dabus

import android.os.Bundle
import com.kainalu.dabus.search.SearchTypesFragment

class MainActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_placeholder)

        fragment = if (savedInstanceState != null) {
            supportFragmentManager.getFragment(savedInstanceState, CURRENT_FRAGMENT)
        } else {
            SearchTypesFragment()
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.placeholder, fragment, CURRENT_FRAGMENT)
            .commit()
    }
}
