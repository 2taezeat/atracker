package com.example.atracker.model.api

import com.example.atracker.model.dto.SignRequest
import com.example.atracker.model.dto.SignResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface SignService {

    @POST("/api/v1/auth/sign/")
    suspend fun signPostApi(
        @Body signRequest : SignRequest

    ) : retrofit2.Response<SignResponse>


}