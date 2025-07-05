package org.raystatic.newsapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ApiArticleDto(
    val source: ApiSourceDto?,
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: String?,
    val content: String?
)