package com.example.atracker.model.local

import android.app.Application

class App: Application() {
    companion object{
        lateinit var prefs : MySharedPreferences
    }

    override fun onCreate() {
        prefs = MySharedPreferences(applicationContext)
        super.onCreate()
    }
}