package com.example.atracker.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import com.example.atracker.BuildConfig
import com.example.atracker.R
import com.example.atracker.databinding.ActivitySplashBinding
import com.example.atracker.model.local.App
import com.example.atracker.ui.login.LoginActivity
import com.example.atracker.utils.StartActivityUtil
import kotlinx.coroutines.*

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


//

//        CoroutineScope(Dispatchers.IO).launch {
//            val at = App.prefs.getValue(BuildConfig.ACCESS_LOCAL_TOKEN)
//            val rt = App.prefs.getValue(BuildConfig.REFRESH_LOCAL_TOKEN)
//
//            Log.d("test_splash", "${at}, ${rt}")
//        }


        val at = App.prefs.getValue(BuildConfig.ACCESS_LOCAL_TOKEN)
        val rt = App.prefs.getValue(BuildConfig.REFRESH_LOCAL_TOKEN)

        Log.d("at_rt_token", "${at}, ${rt}")

        if (at != "") { // 토큰이 있으면
            val mHandler = Handler(Looper.getMainLooper())
            mHandler.postDelayed({
                StartActivityUtil.callActivity(this@SplashActivity, MainActivity())
                finish()
            }, 1000)

        } else {
            val mHandler = Handler(Looper.getMainLooper())
            mHandler.postDelayed({
                StartActivityUtil.callActivity(this@SplashActivity, LoginActivity())
                finish()
            }, 1000)

        }


    }



    override fun onDestroy() {
        Log.d("test_onDestory", "SplashActivity")
        super.onDestroy()
    }
}