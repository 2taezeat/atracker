package com.example.atracker.model.dto

data class CompanySearchRequest (
    val title : String,
    val userDefined : Boolean = true
)
