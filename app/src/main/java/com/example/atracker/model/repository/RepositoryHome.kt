package com.example.atracker.model.repository

import com.example.atracker.BuildConfig
import com.example.atracker.model.RetrofitClient
import com.example.atracker.model.api.HomeAddService
import com.example.atracker.model.dto.CompanySearchRequest
import com.example.atracker.model.dto.CompanySearchResponse
import com.example.atracker.model.dto.LoginRequest
import com.google.gson.JsonObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class RepositoryHome {

    private val homeAddService : HomeAddService = RetrofitClient.getClient(BuildConfig.BASE_URL).create(HomeAddService::class.java)


    suspend fun companySearchCall (accessToken : String, companySearchRequest: CompanySearchRequest, page : Int, size: Int) : retrofit2.Response<CompanySearchResponse> {
        val apiResponse = CoroutineScope(Dispatchers.IO).async{
            homeAddService.companySearch(
                accessToken = accessToken,
                companySearchRequest = companySearchRequest,
                page = 1,
                size = 10)
        }.await()

        return apiResponse
    }

}