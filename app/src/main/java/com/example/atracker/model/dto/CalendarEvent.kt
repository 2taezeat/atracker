package com.example.atracker.model.dto

import java.time.LocalDate

data class CalendarEvent(
    val id: String,
    val text: String,
    val date: LocalDate
    )
