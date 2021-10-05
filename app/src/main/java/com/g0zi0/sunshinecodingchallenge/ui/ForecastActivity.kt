package com.g0zi0.sunshinecodingchallenge.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.g0zi0.sunshinecodingchallenge.R

class ForecastActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainer, ForecastFragment.newInstance()).commit()
    }
}