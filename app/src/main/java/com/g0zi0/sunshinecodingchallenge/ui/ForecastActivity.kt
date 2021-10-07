package com.g0zi0.sunshinecodingchallenge.ui

import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.g0zi0.sunshinecodingchallenge.R


class ForecastActivity : AppCompatActivity() {

    lateinit var fragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fragment = ForecastFragment.newInstance()
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainer, fragment).commit()
    }

    /**
     * override for location permissions
     * invoke onRequestPermissionResult in fragment
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        (fragment as ForecastFragment).onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}