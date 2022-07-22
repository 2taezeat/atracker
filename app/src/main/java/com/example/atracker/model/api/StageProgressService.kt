package com.example.atracker.model.api

import com.example.atracker.model.dto.StageProgressRequest
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.PUT

interface StageProgressService {

    @PUT("/api/v1/stageProgress/")
    suspend fun updateStageProgressApi(
        @Header("Authorization") accessToken : String,
        @Body stageProgressRequest : StageProgressRequest

    ) : retrofit2.Response<Void>


}