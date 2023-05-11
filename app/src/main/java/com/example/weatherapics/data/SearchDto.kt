package com.example.weatherapics.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SearchDto(
    @SerializedName("response")
    val response: SearchResponseDto?
): Serializable

data class SearchResponseDto(
    @SerializedName("status")
    val status: String?,
    @SerializedName("userTier")
    val userTier: String?,
    @SerializedName("total")
    val total: Int?,
    @SerializedName("startIndex")
    val startIndex: Int?,
    @SerializedName("pageSize")
    val pageSize: Int?,
    @SerializedName("currentPage")
    val currentPage: Int?,
    @SerializedName("pages")
    val pages: Int?,
    @SerializedName("orderBy")
    val orderBy: String?,
    @SerializedName("results")
    val results: List<SearchResultDto>
): Serializable

data class SearchResultDto(
    @SerializedName("id")
    val id: String?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("sectionId")
    val sectionId: String?,
    @SerializedName("sectionName")
    val sectionName: String?,
    @SerializedName("webPublicationDate")
    val webPublicationDate: String?,
    @SerializedName("webTitle")
    val webTitle: String?,
    @SerializedName("webUrl")
    val webUrl: String?,
    @SerializedName("apiUrl")
    val apiUrl: String?,
    @SerializedName("fields")
    val fields: SearchFieldDto?,
    @SerializedName("isHosted")
    val isHosted: Boolean,
    @SerializedName("pillarId")
    val pillarId: String?,
    @SerializedName("pillarName")
    val pillarName: String?
): Serializable

data class SearchFieldDto(
    @SerializedName("body")
    val body: String?,
    @SerializedName("thumbnail")
    val thumbnail: String?
): Serializable