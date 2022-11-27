package com.cmc.atracker.model.dto

data class SignRequest(
    val access_token: String,
    val experience_type: String,
    val job_position: String,
    val nick_name: String,
    val primary_email: String,
    val sso: String
)