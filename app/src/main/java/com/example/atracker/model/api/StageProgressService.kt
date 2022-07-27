package com.example.atracker.model.api

import com.example.atracker.model.dto.StageProgressRequest
import retrofit2.http.*

interface StageProgressService {

    @PUT("/api/v1/stageProgress/")
    suspend fun updateStageProgressApi(
        @Header("Authorization") accessToken : String,
        @Body stageProgressRequest : StageProgressRequest

    ) : retrofit2.Response<Void>


    @DELETE("/api/v1/stageProgress/stageProgress/")
    suspend fun deleteStageProgressApi(
        @Header("Authorization") accessToken : String,
        @Query("ids") ids : Array<Int>
    ) : retrofit2.Response<Void>


}