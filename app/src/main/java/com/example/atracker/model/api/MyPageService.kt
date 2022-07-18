package com.example.atracker.model.api

import com.example.atracker.model.dto.LoginRequest
import com.example.atracker.model.dto.MyPageResponse
import com.google.gson.JsonObject
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface MyPageService {

    @GET("/api/v1/user/mypage/")
    suspend fun myPageGetApi(
        @Header("Authorization") accessToken : String,
    ) : retrofit2.Response<MyPageResponse>


}