package com.cmc.atracker.model.dto

data class TestSignApiRequest(
    val email: String,
    val experience_type: String,
    val job_position: String,
    val nick_name: String,
    val sso: String
)