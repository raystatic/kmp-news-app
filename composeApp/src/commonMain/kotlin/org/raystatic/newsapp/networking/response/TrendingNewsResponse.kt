package org.raystatic.newsapp.networking.response

import kotlinx.serialization.Serializable

@Serializable
data class TrendingNewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)