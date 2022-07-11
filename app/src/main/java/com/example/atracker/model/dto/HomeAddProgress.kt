package com.example.atracker.model.dto

import java.time.ZonedDateTime

data class HomeAddProgress(
    val progressName : String,
    var zonedDateTime: ZonedDateTime?
)
