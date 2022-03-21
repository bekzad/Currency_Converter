package com.bekzad.currencyconverter.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bekzad.currencyconverter.Injection
import com.bekzad.currencyconverter.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var vb: ActivityMainBinding
    private val vm: MainViewModel by viewModels {
        Injection.provideViewModelFactory(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vb = ActivityMainBinding.inflate(layoutInflater)
        setContentView(vb.root)
        window.navigationBarColor = ContextCompat.getColor(this, android.R.color.transparent)
        lifecycle.addObserver(vm)
    }
}