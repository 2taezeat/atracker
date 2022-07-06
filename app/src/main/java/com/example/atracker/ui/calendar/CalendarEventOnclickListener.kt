package com.example.atracker.ui.calendar

import android.view.View
import com.example.atracker.model.dto.CalendarEvent

interface CalendarEventOnclickListener {
    fun onClickContainerView(view: View, position: Int, viewTag : String, calendarEvent: CalendarEvent?)

}