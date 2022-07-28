package com.example.atracker.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import com.example.atracker.R
import com.example.atracker.databinding.ActivitySplashBinding
import com.example.atracker.ui.login.LoginActivity
import com.example.atracker.utils.StartActivityUtil

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = ContextCompat.getColor(this, R.color.background_gray)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        Log.d("test_onCreate", "SplashActivity")

//        if (App.prefs.getValue(BuildConfig.ACCESS_LOCAL_TOKEN)!!.isNotBlank()) { // 로컬에 access_token 이 있으면
//            val mHandler = Handler(Looper.getMainLooper())
//            mHandler.postDelayed({
//                startMain()
//            }, 1000)
//
//        } else { // 로컬에 at 가 없으면
//            val mHandler = Handler(Looper.getMainLooper())
//            mHandler.postDelayed({
//                startLogin()
//            }, 1000)
//        }

        val mHandler = Handler(Looper.getMainLooper())
        mHandler.postDelayed({
            StartActivityUtil.callActivity(this@SplashActivity, LoginActivity())
            finish()
        }, 1000)

    }



    override fun onDestroy() {
        Log.d("test_onDestory", "SplashActivity")
        super.onDestroy()
    }
}