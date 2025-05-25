package com.example.newsfeed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.newsfeed.Repositories.NewsRepository
import com.example.newsfeed.Repositories.RoomRepository
import com.example.newsfeed.model.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val repository: NewsRepository,
    private val roomRepository: RoomRepository
) : ViewModel() {


    fun getNewsFlow( apiKey: String): Flow<PagingData<Article>> =
        repository.getNewsFlow(apiKey).flow.cachedIn(viewModelScope)

    val offlineNewsFlow: Flow<PagingData<Article>> = roomRepository
        .getOfflineNewsPager()
        .flow
        .cachedIn(viewModelScope)
}