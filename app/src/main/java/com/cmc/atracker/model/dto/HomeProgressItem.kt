package com.cmc.atracker.model.dto

data class HomeProgressItem(
    val companyTitle : String,
    val jobType : String,
    val myProgress : Int,
    val totalProgress : Int,
    val success : Boolean,
    val applyId : Int
)
