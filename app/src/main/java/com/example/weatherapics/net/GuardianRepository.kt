package com.example.weatherapics.net

import com.example.weatherapics.data.SearchDto
import com.example.weatherapics.data.SearchResultDto
import com.example.weatherapics.model.OrderByEnum
import com.example.weatherapics.model.RightsEnum
import com.example.weatherapics.net.RetrofitClient.USER_KEY
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

interface GuardianRepository {
    fun search(m: Map<String, String>, query: String, orderBy: OrderByEnum, callback: GuardianApiCallback<List<SearchResultDto>>)
}

class GuardianRepositoryFakeImpl : GuardianRepository {
    override fun search(m: Map<String, String>, query: String, orderBy: OrderByEnum, callback: GuardianApiCallback<List<SearchResultDto>>) {
        val list = arrayListOf<SearchResultDto>()

//        for (i in 0 until 100){
//            val srd = SearchResultDto()
//            list.add(srd)
//        }

        callback.onSuccess(list)
    }
}

class GuardianRepositoryImpl(private val api: GuardianApi) : GuardianRepository {

    override fun search(m: Map<String, String>, query: String, orderBy: OrderByEnum, callback: GuardianApiCallback<List<SearchResultDto>>) {
        api.search(orderBy.key, query, USER_KEY, m).enqueue(object : Callback<SearchDto> {
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