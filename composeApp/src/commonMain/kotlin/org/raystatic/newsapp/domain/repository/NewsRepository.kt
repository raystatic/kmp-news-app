package org.raystatic.newsapp.domain.repository

import org.raystatic.newsapp.domain.model.NewsArticle
import org.raystatic.newsapp.util.Result

interface NewsRepository {
    suspend fun getNews(category: String, page: Int): Result<List<NewsArticle>, Exception>
}