package com.example.atracker.model.dto

data class UpdateApplyRequest(
    val apply_id : Int,
    val company: Company,
    val job_position: String,
    val job_type: String,
    val stages: List<Stage>
)