package com.example.atracker.model.api

import com.example.atracker.model.dto.CompanySearchRequest
import com.example.atracker.model.dto.CompanySearchResponse
import com.example.atracker.model.dto.LoginRequest
import com.google.gson.JsonObject
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface HomeAddService {

    @POST("/api/v1/company/search")
    suspend fun companySearch(
        @Header("Authorization") accessToken : String,
        @Body companySearchRequest: CompanySearchRequest,
        @Query("page") page : Int,
        @Query("size") size : Int

        ) : retrofit2.Response<CompanySearchResponse>


}