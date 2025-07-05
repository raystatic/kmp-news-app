package org.raystatic.newsapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class NewsApiResponseDto(
    val status: String?,
    val totalResults: Int?,
    val articles: List<ApiArticleDto>?
)