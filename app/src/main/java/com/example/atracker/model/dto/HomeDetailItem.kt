package com.example.atracker.model.dto

data class HomeDetailItem(
    val progressType : String,
    val itemType : String,
    val totalReviewBody : String?,
    val questionBody : String?,
    val answerBody : String?
)
