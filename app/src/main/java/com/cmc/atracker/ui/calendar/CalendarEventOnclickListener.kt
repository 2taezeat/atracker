package com.cmc.atracker.ui.calendar

import android.view.View
import com.cmc.atracker.model.dto.CalendarEvent

interface CalendarEventOnclickListener {
    fun onClickContainerView(view: View, position: Int, viewTag : String, calendarEvent: CalendarEvent?)

}