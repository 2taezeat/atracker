package com.example.atracker.model.dto

import java.time.LocalDate

data class Event(
    val id: String,
    val text: String,
    val date: LocalDate
    )
