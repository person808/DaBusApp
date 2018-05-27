package com.kainalu.dabus

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    protected lateinit var fragment: Fragment

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        supportFragmentManager.putFragment(outState, CURRENT_FRAGMENT, fragment)
    }

    companion object {
        internal const val CURRENT_FRAGMENT = "currentFragment"
    }
}