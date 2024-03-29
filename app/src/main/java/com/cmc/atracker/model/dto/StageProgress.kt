package com.cmc.atracker.model.dto

data class StageProgress(
    val event_at : String,
    val id: Int,
    val order: Int,
    val stage_contents: List<StageContent>,
    val stage_id: Int,
    val stage_title: String,
    val status: String
)