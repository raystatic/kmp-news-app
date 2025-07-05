package org.raystatic.newsapp.data.repository

import org.raystatic.newsapp.data.datasource.remote.NewsRemoteDataSource
import org.raystatic.newsapp.data.mapper.toDomainArticleList
import org.raystatic.newsapp.domain.model.NewsArticle
import org.raystatic.newsapp.domain.repository.NewsRepository
import org.raystatic.newsapp.util.Result
import org.raystatic.newsapp.util.map

class NewsRepositoryImpl(
    private val remoteDataSource: NewsRemoteDataSource
    // private val localDataSource: NewsLocalDataSource
) : NewsRepository {

    override suspend fun getNews(
        category: String,
        page: Int
    ): Result<List<NewsArticle>, Exception> {
        return remoteDataSource.fetchNews(category, page).map { newsApiResponseDto ->
            newsApiResponseDto.articles.toDomainArticleList()
        }
    }
}