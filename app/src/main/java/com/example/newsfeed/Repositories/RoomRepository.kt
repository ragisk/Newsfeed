package com.example.newsfeed.Repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.newsfeed.NewsApiService
import com.example.newsfeed.NewsUtils
import com.example.newsfeed.model.Article
import com.example.newsfeed.room_db.ArticleDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RoomRepository @Inject constructor(
    private val apiService: NewsApiService,
    private val dao: ArticleDao
)  {


    suspend fun refreshArticles() {
        try {
            val requiredArticles = 50
            val articles = mutableListOf<Article>()
            var currentPage = 1
            while (articles.size < requiredArticles) {
                val response = apiService.getLatestNews(NewsUtils.apiKey, currentPage.toString())
                response.results?.let {
                    articles.addAll(it)
                }
                if (response.results.isNullOrEmpty()) break
                currentPage++
            }
//            val response = apiService.getLatestNews( NewsUtils.apiKey)
//            val entities = response.results
            dao.clearArticles()
            dao.insertArticles(articles)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getOfflineNewsPager(): Pager<Int, Article> = Pager(
        config = PagingConfig(pageSize = 10),
        pagingSourceFactory = { dao.getArticlesPaging() }
    )
}