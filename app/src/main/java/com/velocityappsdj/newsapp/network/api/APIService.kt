package com.velocityappsdj.newsapp.network.api

import com.velocityappsdj.newsapp.network.models.NewsResponse
import retrofit2.Response

import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {

    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query("category") category: String = "general",
        @Query("country") country: String = "in",
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int = 20
    ): Response<NewsResponse>

    @GET("everything")
    suspend fun searchNews(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int = 20
    ): Response<NewsResponse>
}