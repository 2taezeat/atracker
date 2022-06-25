package com.example.atracker.model.api

import com.example.atracker.model.dto.LoginRequest
import com.google.gson.JsonObject
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface LoginService {

    @POST("/api/v1/auth/sign")
    suspend fun loginServiceFun(
        @Header("Authorization") accessToken : String,
        @Body loginRequest : LoginRequest

    ) : retrofit2.Response<JsonObject>


}