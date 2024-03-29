package com.cmc.atracker.model.dto

data class HomeDetailItem(
    val progressType : Int, // 몇 번째 단계
    val progressName : String, // 서류, 사전과제 등
    val itemType : ProgressItemBodyType, //
    val freeTitle : String?,
    val freeBody : String?,
    val totalReviewBody : String?,
    val questionBody : String?,
    val answerBody : String?,
    val qnaReviewBody : String?,
    val progressIsPassing : IsPassing?,

    val contentOrder : Int,
    val contentId : Int,
    val stageRealId : Int
)
