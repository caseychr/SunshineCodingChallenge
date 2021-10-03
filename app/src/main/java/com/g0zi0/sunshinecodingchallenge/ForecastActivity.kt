package com.g0zi0.sunshinecodingchallenge

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.g0zi0.sunshinecodingchallenge.ui.ForecastFragment

class ForecastActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainer, ForecastFragment.newInstance()).commit()
    }
}