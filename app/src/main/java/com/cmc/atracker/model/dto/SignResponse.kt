package com.cmc.atracker.model.dto

data class SignResponse(
    val access_token: String,
    val message: String,
    val refresh_token: String,
    val status: Int
)