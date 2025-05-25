package com.example.newsfeed.Repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.newsfeed.NewsApiService
import com.example.newsfeed.NewsPagingSource
import com.example.newsfeed.model.Article
import com.example.newsfeed.room_db.ArticleDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepository @Inject constructor(
    private val apiService: NewsApiService,
    private val dao: ArticleDao
) {
    fun getNewsFlow( apiKey: String): Pager<String, Article> =
     Pager(
        config = PagingConfig(pageSize = 10),
        pagingSourceFactory = {
            NewsPagingSource(apiService, apiKey,dao)
        }
    )
}