package com.cmc.atracker.model.dto

data class Apply(
    val apply_id: Int,
    val company_id: Int,
    val company_name: String,
    val job_position: String,
    val job_type: String,
    val stage_progress: List<StageProgress>
)