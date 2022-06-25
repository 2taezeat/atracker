package com.example.atracker.model.dto

import com.google.gson.annotations.SerializedName

data class CompanySearchRequest (
    val title : String,
    @SerializedName("user_defined")
    val userDefined : Boolean = true
)
