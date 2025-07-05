package org.raystatic.newsapp.data.datasource.remote

import org.raystatic.newsapp.data.model.NewsApiResponseDto
import org.raystatic.newsapp.util.Result

interface NewsRemoteDataSource {
    suspend fun fetchNews(category: String, page: Int): Result<NewsApiResponseDto, Exception>
}