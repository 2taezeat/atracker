package com.example.atracker.model.dto

data class TestSignApiResponse(
    val access_token: String,
    val refresh_token: String,
    val status: Int
)