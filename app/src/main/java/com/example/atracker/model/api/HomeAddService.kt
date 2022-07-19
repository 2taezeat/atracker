package com.example.atracker.model.api

import com.example.atracker.model.dto.*
import com.google.gson.JsonObject
import retrofit2.http.*

interface HomeAddService {

    @POST("/api/v1/company/search/")
    suspend fun companySearchPostApi(
        @Header("Authorization") accessToken : String,
        @Body companySearchRequest: CompanySearchRequest,
        @Query("page") page : Int,
        @Query("size") size : Int
        ) : retrofit2.Response<CompanySearchResponse>


    @GET("/api/v1/stage/")
    suspend fun stageGetApi(
        @Header("Authorization") accessToken : String
    ) : retrofit2.Response<StageResponse>


    @POST("/api/v1/apply/")
    suspend fun createApplyPostApi(
        @Header("Authorization") accessToken : String,
        @Body createApplyRequest : CreateApplyRequest
    ) : retrofit2.Response<Void>


}