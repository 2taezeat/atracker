package com.example.atracker.model.repository

import com.example.atracker.BuildConfig
import com.example.atracker.model.RetrofitClient
import com.example.atracker.model.api.HomeService
import com.example.atracker.model.api.StageProgressService
import com.example.atracker.model.dto.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class RepositoryHome {

    private val homeService : HomeService = RetrofitClient.getClient(BuildConfig.BASE_URL).create(HomeService::class.java)
    private val stageProgressService : StageProgressService = RetrofitClient.getClient(BuildConfig.BASE_URL).create(StageProgressService::class.java)


    suspend fun companySearchPostCall (accessToken : String, companySearchRequest: CompanySearchRequest, page : Int, size: Int) : retrofit2.Response<CompanySearchResponse> {
        val apiResponse = CoroutineScope(Dispatchers.IO).async{
            homeService.companySearchPostApi(
                accessToken = accessToken,
                companySearchRequest = companySearchRequest,
                page = page,
                size = size)
        }.await()

        return apiResponse
    }

    suspend fun companyAddPostCall (accessToken : String, createCompanyRequest: List<CreateCompanyRequestItem> ) : retrofit2.Response<CreateCompanyResponse> {
        val apiResponse = CoroutineScope(Dispatchers.IO).async{
            homeService.companyAddPostApi(
                accessToken = accessToken,
                companyCreateRequests = createCompanyRequest,
            )
        }.await()

        return apiResponse
    }



    suspend fun stageGetCall (accessToken : String ) : retrofit2.Response<StageResponse>{
        val apiResponse = CoroutineScope(Dispatchers.IO).async {
            homeService.stageGetApi(accessToken = accessToken)
        }.await()

        return apiResponse
    }

    suspend fun createApplyPostCall (accessToken : String, createApplyRequest : CreateApplyRequest ) : retrofit2.Response<Void> {
        val apiResponse = CoroutineScope(Dispatchers.IO).async{
            homeService.createApplyPostApi(
                accessToken = accessToken,
                createApplyRequest = createApplyRequest
                )
        }.await()

        return apiResponse
    }


    suspend fun applyGetCall (accessToken : String, applyIds : Array<Int>?, includeContent : Boolean? ) : retrofit2.Response<ApplyResponse>{
        val apiResponse = CoroutineScope(Dispatchers.IO).async {
            homeService.applyGetApi(accessToken = accessToken, applyIds = applyIds, includeContent = includeContent)
        }.await()

        return apiResponse
    }

    suspend fun updateApplyPutCall (accessToken : String, updateApplyRequest : UpdateApplyRequest ) : retrofit2.Response<Void> {
        val apiResponse = CoroutineScope(Dispatchers.IO).async{
            homeService.updateApplyPutApi(
                accessToken = accessToken,
                updateApplyRequest = updateApplyRequest
            )
        }.await()

        return apiResponse
    }

    suspend fun deleteApplyCall (accessToken : String, ids : Array<Int> ) : retrofit2.Response<Void> {
        val apiResponse = CoroutineScope(Dispatchers.IO).async{
            homeService.deleteApplyApi(
                accessToken = accessToken,
                ids = ids
            )
        }.await()

        return apiResponse
    }


    suspend fun updateStageProgressCall (accessToken : String, stageProgressRequest: StageProgressRequest ) : retrofit2.Response<Void> {
        val apiResponse = CoroutineScope(Dispatchers.IO).async{
            stageProgressService.updateStageProgressApi(
                accessToken = accessToken,
                stageProgressRequest = stageProgressRequest
            )
        }.await()

        return apiResponse
    }


    suspend fun deleteStageProgressCall (accessToken : String, ids : Array<Int> ) : retrofit2.Response<Void> {
        val apiResponse = CoroutineScope(Dispatchers.IO).async{
            homeService.deleteApplyApi(
                accessToken = accessToken,
                ids = ids
            )
        }.await()

        return apiResponse
    }




}