package com.example.atracker.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.core.content.ContextCompat
import com.example.atracker.R
import com.example.atracker.databinding.ActivityMainBinding
import com.example.atracker.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mHandler = Handler(Looper.getMainLooper())
        mHandler.postDelayed({
            startLogin()
        }, 1000)


    }

    fun startLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        ContextCompat.startActivity(this, intent, null)
        finish()
    }
}