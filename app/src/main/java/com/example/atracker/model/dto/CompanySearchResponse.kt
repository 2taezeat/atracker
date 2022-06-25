package com.example.atracker.model.dto

import com.google.gson.annotations.SerializedName

data class CompanySearchResponse(
    val content_size: Int,
    @SerializedName("contents")
    val companySearchContents: List<CompanySearchContent>,
    val current_page_no: Int,
    val empty: Boolean,
    val first: Boolean,
    val has_next: Boolean,
    val has_prev: Boolean,
    val last: Boolean,
    val next_page_no: Int,
    val prev_count: Int,
    val prev_page_no: Int,
    val remain_count: Int,
    val size: Int,
    val total_count: Int
)