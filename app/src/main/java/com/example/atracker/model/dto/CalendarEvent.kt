package com.example.atracker.model.dto

import java.time.LocalDate
import java.time.ZonedDateTime

data class CalendarEvent(
    val id: String,
    val eventTitle: String?,
    //val date: LocalDate,
    val zonedDateTime: ZonedDateTime,
    val eventLocation : String?,
    val eventNote : String?
    )
