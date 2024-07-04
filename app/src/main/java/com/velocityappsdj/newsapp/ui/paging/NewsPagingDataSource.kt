package com.velocityappsdj.newsapp.ui.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.velocityappsdj.newsapp.network.models.Article
import com.velocityappsdj.sortlymobileproject.common.NetworkError
import com.velocityappsdj.sortlymobileproject.common.Result

class NewsPagingDataSource(private val apiCallFunction: suspend (page: Int) -> Result<List<Article>, NetworkError>) :
    PagingSource<Int, Article>() {

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val page = params.key ?: 1

        return when (val result = apiCallFunction(page)) {

            is Result.Success -> {
                val articles = result.data
                LoadResult.Page(
                    data = articles ?: emptyList(),
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = if (articles.isEmpty()) null else page + 1
                )
            }

            is Result.Error -> {
                when (result.error) {
                    NetworkError.MAX_RESULTS_REACHED -> LoadResult.Page(
                        data = emptyList(),
                        prevKey = if (page == 1) null else page - 1, nextKey = null
                    )

                    else -> LoadResult.Error(Exception(result.error.name))
                }

            }
        }
    }
}