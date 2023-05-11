package com.example.weatherapics.net

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val BASE_URL = "https://content.guardianapis.com/"
    const val USER_KEY = "e7caa497-5e2a-4e3d-b10f-7194654bc44e"

    val retrofit: GuardianApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GuardianApi::class.java)
    }
}

interface GuardianApiCallback<T> {
    fun onSuccess(t: T)
    fun onError(msg: String)
    fun onFailure(e: Throwable)
}