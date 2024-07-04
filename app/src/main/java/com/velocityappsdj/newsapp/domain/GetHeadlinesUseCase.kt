package com.velocityappsdj.newsapp.domain

import com.velocityappsdj.newsapp.network.models.Article
import com.velocityappsdj.newsapp.repo.NewsRepo
import com.velocityappsdj.sortlymobileproject.common.NetworkError
import com.velocityappsdj.sortlymobileproject.common.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetHeadlinesUseCase @Inject constructor(val newsRepo: NewsRepo) {
    suspend operator fun invoke(page: Int): Result<List<Article>, NetworkError> {
        return withContext(Dispatchers.Default) {
            when (val result = newsRepo.getTopHeadlines(page)) {
                is Result.Error -> Result.Error(result.error)
                is Result.Success -> {
                    val list = result.data.articles
                    list?.let {
                        Result.Success(it)
                    } ?: Result.Success(emptyList())
                }
            }
        }
    }
}