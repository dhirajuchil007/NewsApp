package com.velocityappsdj.newsapp.ui.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.velocityappsdj.newsapp.domain.GetHeadlinesUseCase
import com.velocityappsdj.newsapp.domain.GetSearchResultsUseCase
import com.velocityappsdj.newsapp.network.models.Article
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsPager @Inject constructor(
    private val getHeadlinesUseCase: GetHeadlinesUseCase,
    private val getSearchResultsUseCase: GetSearchResultsUseCase

) {

    fun getHeadlinesPager(): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = {
                NewsPagingDataSource {
                    getHeadlinesUseCase(page = it)
                }
            }
        ).flow
    }

    fun getSearchResultsPager(query: String): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = {
                NewsPagingDataSource {
                    getSearchResultsUseCase(
                        page = it,
                        query = query
                    )
                }

            }).flow
    }
}