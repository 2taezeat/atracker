package com.example.atracker.ui.home

import android.view.View

interface HomeProgressOnclickListener {
    fun onClickContainerView(view: View, position: Int, viewTag : String)
}