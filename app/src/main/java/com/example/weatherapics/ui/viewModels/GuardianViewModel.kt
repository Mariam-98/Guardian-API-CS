package com.example.weatherapics.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapics.data.SearchResultDto
import com.example.weatherapics.net.GuardianApiCallback
import com.example.weatherapics.net.GuardianRealDataRepositoryImpl
import com.example.weatherapics.net.GuardianRepository
import com.example.weatherapics.net.RetrofitClient.retrofit

class GuardianViewModel : ViewModel() {
    private val guardianRepo by lazy { GuardianRealDataRepositoryImpl(retrofit) }

    private val _guardianLiveData = MutableLiveData<List<SearchResultDto>>()
    val guardianLiveData: LiveData<List<SearchResultDto>>
        get() = _guardianLiveData


    fun setGuardianData(query: String, map: Map<String, String>) {
        guardianRepo.search(query, map, object : GuardianApiCallback<List<SearchResultDto>> {
            override fun onSuccess(t: List<SearchResultDto>) {
                _guardianLiveData.value = t
            }

            override fun onError(msg: String) {
                TODO("Not yet implemented")
            }

            override fun onFailure(e: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    private val _orderByFilter = MutableLiveData<String>()
    val orderByFilter: LiveData<String>
        get() = _orderByFilter

    private val _showElementsFilter = MutableLiveData<String>()
    val showElementsFilter: LiveData<String>
        get() = _showElementsFilter

    fun setOrderByFilter(filter: String) {
        _orderByFilter.value = filter
    }

    fun setShowElementsFilter(filter: String) {
        _orderByFilter.value = filter
    }
}


class GuardianDemoViewModel(private val guardianRepository: GuardianRepository) : ViewModel() {

    var someText = "akdsbkahdsbasd"

    val searchResultListLiveData = MutableLiveData<List<SearchResultDto>>()

    fun search(query: String, map: Map<String, String>) {
        guardianRepository.search(query, map, object : GuardianApiCallback<List<SearchResultDto>> {
            override fun onSuccess(t: List<SearchResultDto>) {
                searchResultListLiveData.value = tq
            }

            override fun onError(msg: String) {
                TODO("Not yet implemented")
            }

            override fun onFailure(e: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }
}