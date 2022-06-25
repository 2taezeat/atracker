package com.example.atracker.model.api

import com.example.atracker.model.dto.LoginRequest
import com.google.gson.JsonObject
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface HomeAddService {

    @POST("/api/v1/company/searcg")
    suspend fun companySearch(
        @Header("Authorization") AccessToken : String,
        @Body loginRequest : LoginRequest,
        @Query("page") page : Int,
        @Query("size") size : Int


        ) : retrofit2.Response<JsonObject>


}