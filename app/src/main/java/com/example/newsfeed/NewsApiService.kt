package com.example.newsfeed

import com.example.newsfeed.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    @GET("latest")
    suspend fun getLatestNews(
        @Query("apikey") apiKey: String,
        @Query("page") page: String? = null
    ): NewsResponse
}