package org.raystatic.newsapp.presentation.model

import org.raystatic.newsapp.domain.model.NewsArticle

data class NewsListUiState(
    val isLoading: Boolean = false,
    val articles: List<NewsArticle> = emptyList(),
    val error: String? = null,
    val selectedCategory: String = "technology", // Default category
    val page: Int = 1,
    val canLoadMore: Boolean = true // To know if we can paginate further
)