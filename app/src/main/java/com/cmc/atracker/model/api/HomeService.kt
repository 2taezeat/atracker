package com.cmc.atracker.model.api

import com.cmc.atracker.model.dto.*
import retrofit2.http.*

interface HomeService {

    @POST("/api/v1/company/search/")
    suspend fun companySearchPostApi(
        @Header("Authorization") accessToken : String,
        @Body companySearchRequest: CompanySearchRequest,
        @Query("page") page : Int,
        @Query("size") size : Int
        ) : retrofit2.Response<CompanySearchResponse>


    @POST("/api/v1/company/companies/")
    suspend fun companyAddPostApi(
        @Header("Authorization") accessToken : String,
        @Body companyCreateRequests: List<CreateCompanyRequestItem>,
    ) : retrofit2.Response<CreateCompanyResponse>




    @GET("/api/v1/stage/")
    suspend fun stageGetApi(
        @Header("Authorization") accessToken : String
    ) : retrofit2.Response<StageResponse>


    @POST("/api/v1/apply/")
    suspend fun createApplyPostApi(
        @Header("Authorization") accessToken : String,
        @Body createApplyRequest : CreateApplyRequest
    ) : retrofit2.Response<Void>


    @GET("/api/v1/apply/")
    suspend fun applyGetApi(
        @Header("Authorization") accessToken : String,
        @Query("applyIds") applyIds : Array<Int>?,
        @Query("includeContent") includeContent : Boolean?
    ) : retrofit2.Response<ApplyResponse>

    @PUT("/api/v1/apply/")
    suspend fun updateApplyPutApi(
        @Header("Authorization") accessToken : String,
        @Body updateApplyRequest : UpdateApplyRequest
    ) : retrofit2.Response<Void>


    @DELETE("/api/v1/apply/applies/")
    suspend fun deleteApplyApi(
        @Header("Authorization") accessToken : String,
        @Query("ids") ids : Array<Int>
    ) : retrofit2.Response<Void>

}