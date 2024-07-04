package com.velocityappsdj.newsapp.domain

import com.velocityappsdj.newsapp.network.models.Article
import com.velocityappsdj.newsapp.repo.NewsRepo
import com.velocityappsdj.sortlymobileproject.common.NetworkError
import com.velocityappsdj.sortlymobileproject.common.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetSearchResultsUseCase @Inject constructor(val repo: NewsRepo) {
    suspend operator fun invoke(page: Int, query: String): Result<List<Article>, NetworkError> {
        return withContext(Dispatchers.Default) {
            when (val result = repo.searchNews(page, query)) {
                is Result.Error -> Result.Error(result.error)
                is Result.Success -> {
                    val articles = result.data.articles
                    articles?.let {
                        Result.Success(it)
                    } ?: Result.Success(emptyList())
                }
            }
        }
    }
}