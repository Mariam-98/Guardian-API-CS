package com.example.weatherapics.net

import com.example.weatherapics.data.SearchDto
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface GuardianApi {

//    @GET("search?")
//    fun search(
//        @Query("show-fields") showFields: String,
//        @Query("q") q: String,
//        @Query("api-key") apiKey: String
//    ): retrofit2.Call<SearchDto>

    @GET("search?")
    fun search(
        @Query("order-by") orderBy: String,
        @Query("q") q: String,
        @Query("api-key") apiKey: String,
        @QueryMap map: Map<String,String>
    ): retrofit2.Call<SearchDto>
}