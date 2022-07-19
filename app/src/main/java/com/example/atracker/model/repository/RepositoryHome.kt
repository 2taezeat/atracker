package com.example.atracker.model.repository

import com.example.atracker.BuildConfig
import com.example.atracker.model.RetrofitClient
import com.example.atracker.model.api.HomeAddService
import com.example.atracker.model.dto.CompanySearchRequest
import com.example.atracker.model.dto.CompanySearchResponse
import com.example.atracker.model.dto.CreateApplyRequest
import com.example.atracker.model.dto.StageResponse
import com.google.gson.JsonObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class RepositoryHome {

    private val homeAddService : HomeAddService = RetrofitClient.getClient(BuildConfig.BASE_URL).create(HomeAddService::class.java)


    suspend fun companySearchPostCall (accessToken : String, companySearchRequest: CompanySearchRequest, page : Int, size: Int) : retrofit2.Response<CompanySearchResponse> {
        val apiResponse = CoroutineScope(Dispatchers.IO).async{
            homeAddService.companySearchPostApi(
                accessToken = accessToken,
                companySearchRequest = companySearchRequest,
                page = 1,
                size = 10)
        }.await()

        return apiResponse
    }


    suspend fun stageGetCall (accessToken : String ) : retrofit2.Response<StageResponse>{
        val apiResponse = CoroutineScope(Dispatchers.IO).async {
            homeAddService.stageGetApi(accessToken = accessToken)
        }.await()

        return apiResponse
    }

    suspend fun createApplyPostCall (accessToken : String, createApplyRequest : CreateApplyRequest ) : retrofit2.Response<JsonObject> {
        val apiResponse = CoroutineScope(Dispatchers.IO).async{
            homeAddService.createApplyPostApi(
                accessToken = accessToken,
                createApplyRequest = createApplyRequest
                )
        }.await()

        return apiResponse
    }



}