package com.velocityappsdj.newsapp.repo

import com.velocityappsdj.newsapp.network.api.APIService
import com.velocityappsdj.newsapp.network.models.NewsResponse
import com.velocityappsdj.sortlymobileproject.common.NetworkError
import com.velocityappsdj.sortlymobileproject.common.Result
import com.velocityappsdj.sortlymobileproject.common.getNetworkErrorFromCode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class NewsRepoImpl(val apiService: APIService) : NewsRepo {
    override suspend fun getTopHeadlines(page: Int): Result<NewsResponse, NetworkError> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getTopHeadlines(page = page)
                if (response.isSuccessful) {
                    response.body()?.let {
                        Result.Success(it)
                    }
                        ?: Result.Error(NetworkError.UNKNOWN)
                } else {
                    Result.Error(getNetworkErrorFromCode(response.code()))
                }
            } catch (e: Exception) {
                Result.Error(NetworkError.UNKNOWN)
            }
        }
    }

    override suspend fun searchNews(page: Int, query: String): Result<NewsResponse, NetworkError> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.searchNews(page = page, query = query)
                if (response.isSuccessful) {
                    response.body()?.let {
                        Result.Success(it)
                    }
                        ?: Result.Error(NetworkError.UNKNOWN)
                } else {
                    Result.Error(getNetworkErrorFromCode(response.code()))
                }
            } catch (e: Exception) {
                Result.Error(NetworkError.UNKNOWN)
            }
        }
    }

}