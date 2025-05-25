package com.example.newsfeed

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.newsfeed.model.Article
import com.example.newsfeed.room_db.ArticleDao

class NewsPagingSource(
    private val apiService: NewsApiService,
    private val apiKey: String,
    private val dao: ArticleDao
) : PagingSource<String, Article>() {

    override suspend fun load(params: LoadParams<String>): LoadResult<String, Article> {
        return try {
            val pageToken = params.key
            val response = apiService.getLatestNews(
                apiKey = apiKey,
                page = pageToken
            )
            if (dao.getArticleCount()<=30){
                dao.insertArticles(response.results)
            }
            LoadResult.Page(
                data = response.results,
                prevKey = null,
                nextKey = response.nextPage
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<String, Article>): String? = null
}
