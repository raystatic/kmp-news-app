package org.raystatic.newsapp.domain.usecases

import org.raystatic.newsapp.domain.model.NewsArticle
import org.raystatic.newsapp.domain.repository.NewsRepository
import org.raystatic.newsapp.util.Result

class GetNewsUseCase(private val newsRepository: NewsRepository) {
    suspend operator fun invoke(category: String, page: Int): Result<List<NewsArticle>, Exception> {
        // Basic validation or pre-processing can go here if needed
        if (category.isBlank()) {
            return Result.Error(IllegalArgumentException("Category cannot be blank"))
        }
        if (page < 1) {
            return Result.Error(IllegalArgumentException("Page number must be at least 1"))
        }
        return newsRepository.getNews(category, page)
    }
}