package com.cmc.atracker.model.dto

data class CreateApplyRequest(
    val company: Company,
    val job_position: String,
    val job_type: String,
    val stages: List<Stage>
)