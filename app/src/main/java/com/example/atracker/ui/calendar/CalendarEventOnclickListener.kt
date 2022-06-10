package com.example.atracker.ui.calendar

import android.view.View

interface CalendarEventOnclickListener {
    fun onClickContainerView(view: View, position: Int, viewTag : String)

}