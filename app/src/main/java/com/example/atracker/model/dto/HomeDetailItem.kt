package com.example.atracker.model.dto

data class HomeDetailItem(
    val progressType : Int, // 몇 번째 단계
    val progressName : String, // 서류, 사전과제 등
    val itemType : String, //
    val totalReviewBody : String?,
    val questionBody : String?,
    val answerBody : String?
)