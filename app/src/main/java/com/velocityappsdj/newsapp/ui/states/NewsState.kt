package com.velocityappsdj.newsapp.ui.states

import androidx.paging.PagingData
import com.velocityappsdj.newsapp.network.models.Article
import kotlinx.coroutines.flow.Flow

sealed class NewsState {

    data object Loading : NewsState()
    data class Success(val articles: Flow<PagingData<Article>>) : NewsState()
    data class Error(val msg: String) : NewsState()

}