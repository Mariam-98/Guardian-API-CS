package com.example.weatherapics.net

import android.provider.ContactsContract.Data
import com.example.weatherapics.data.SearchDto
import com.example.weatherapics.data.SearchResultDto
import com.example.weatherapics.net.RetrofitClient.USER_KEY
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

interface GuardianRepository {
    fun search(query: String, map: Map<String, String>, callback: GuardianApiCallback<List<SearchResultDto>>)
}

class GuardianRealDataRepositoryImpl(private val api: GuardianApi) : GuardianRepository {
    override fun search(query: String, map: Map<String, String>, callback: GuardianApiCallback<List<SearchResultDto>>) {
        api.search(query, USER_KEY, "body,thumbnail", map).enqueue(object : Callback<SearchDto> {
            override fun onResponse(call: Call<SearchDto>, response: Response<SearchDto>) {
                if (response.isSuccessful) {
                    callback.onSuccess(response.body()?.response?.results ?: emptyList())
                } else {
                    callback.onError(response.message())
                }
            }

            override fun onFailure(call: Call<SearchDto>, t: Throwable) {
                callback.onFailure(t)
            }
        })
    }
}

class GuardianFakeDataRepositoryImpl : GuardianRepository {
    override fun search(query: String, map: Map<String, String>, callback: GuardianApiCallback<List<SearchResultDto>>) {
        val list = arrayListOf<SearchResultDto>()
        for (i in 0 until 1000) {
            list.add(SearchResultDto(...))
        }
        callback.onSuccess(list)
    }
}

class GuardianCachedDataRepositoryImpl() : GuardianRepository {
    override fun search(query: String, map: Map<String, String>, callback: GuardianApiCallback<List<SearchResultDto>>) {
        val list = cachedData.getNews()
        callback.onSuccess(list)
    }
}

class GuardianDataBaseDataRepositoryImpl(val db : MyDataBase) : GuardianRepository {
    override fun search(query: String, map: Map<String, String>, callback: GuardianApiCallback<List<SearchResultDto>>) {
        val list = db.list
        callback.onSuccess(list)
    }
}

data class MyDataBase(val list : List<SearchResultDto>) : DataBase


interface DataBase


