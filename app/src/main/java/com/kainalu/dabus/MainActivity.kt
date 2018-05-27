package com.kainalu.dabus

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.kainalu.dabus.search.SearchTypesFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .replace(R.id.placeholder, SearchTypesFragment())
            .commit()
    }
}
