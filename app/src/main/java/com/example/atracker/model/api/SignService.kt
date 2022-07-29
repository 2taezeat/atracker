package com.example.atracker.model.api

import com.example.atracker.model.dto.*
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface SignService {

    @POST("/api/v1/auth/sign/")
    suspend fun signPostApi(
        @Body signRequest : SignRequest

    ) : retrofit2.Response<SignResponse>


    @POST("/api/v1/auth/refresh/")
    suspend fun refreshTokenPostApi(
        @Body tokenRefreshRequest: TokenRefreshRequest

    ) : retrofit2.Response<TokenRefreshResponse>

    @POST("/api/v1/auth/signout/")
    suspend fun signOutPostApi(
        @Header("Authorization") accessToken : String
    ) : retrofit2.Response<Void>


    ////////////////////////////////////////////////////// test api //////////////////////////////

    @POST("/api/v1/auth/test/sign")
    suspend fun testSignApi(
        @Body testSignApiRequest: TestSignApiRequest
    ) : retrofit2.Response<TestSignApiResponse>


}