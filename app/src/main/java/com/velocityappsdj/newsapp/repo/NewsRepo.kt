package com.velocityappsdj.newsapp.repo

import com.velocityappsdj.newsapp.network.models.NewsResponse
import com.velocityappsdj.sortlymobileproject.common.NetworkError
import com.velocityappsdj.sortlymobileproject.common.Result
import retrofit2.Response

interface NewsRepo {
    suspend fun getTopHeadlines(page: Int): Result<NewsResponse, NetworkError>

    suspend fun searchNews(page: Int, query: String): Result<NewsResponse, NetworkError>
}