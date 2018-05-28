package com.kainalu.dabus

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem

abstract class BaseActivity : AppCompatActivity() {

    protected lateinit var fragment: Fragment

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        supportFragmentManager.putFragment(outState, CURRENT_FRAGMENT, fragment)
    }

    // Allow fragment to handle menu selections by returning false.
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.favorites_button -> false
            else -> false
        }
    }

    companion object {
        internal const val CURRENT_FRAGMENT = "currentFragment"
    }
}