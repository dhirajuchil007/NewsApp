package com.velocityappsdj.newsapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.velocityappsdj.newsapp.network.models.Article
import com.velocityappsdj.newsapp.ui.paging.NewsPager
import com.velocityappsdj.newsapp.ui.states.NewsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class NewsViewModel @Inject constructor(private val newsPager: NewsPager) :
    ViewModel() {

    private val _state = MutableStateFlow<NewsState>(NewsState.Loading)
    val state = _state

    private val _query = MutableStateFlow("")
    val query = _query

    init {
        viewModelScope.launch {
            _state.value = NewsState.Success(getHeadlines())
            query.debounce(500).distinctUntilChanged().flatMapLatest {
                if (it.isEmpty()) {
                    getHeadlines()
                } else {
                    getSearchedHeadlines(it)
                }
            }.flowOn(Dispatchers.IO).collectLatest {
                _state.value = NewsState.Success(flowOf(it))
            }
        }
    }


    private fun getHeadlines(): Flow<PagingData<Article>> {
        _state.value = NewsState.Loading
        return newsPager.getHeadlinesPager().cachedIn(viewModelScope)

    }

    fun onQueryChanged(query: String) {
        _query.value = query
    }

    private fun getSearchedHeadlines(query: String): Flow<PagingData<Article>> {
        _state.value = NewsState.Loading

        return newsPager.getSearchResultsPager(query).cachedIn(viewModelScope)

    }

}