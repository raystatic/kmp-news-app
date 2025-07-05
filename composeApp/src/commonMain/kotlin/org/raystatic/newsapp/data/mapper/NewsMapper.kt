package org.raystatic.newsapp.data.mapper

import org.raystatic.newsapp.data.model.ApiArticleDto
import org.raystatic.newsapp.data.model.ApiSourceDto
import org.raystatic.newsapp.domain.model.NewsArticle
import org.raystatic.newsapp.domain.model.Source

fun ApiSourceDto?.toDomainSource(): Source {
    return Source(
        id = this?.id ?: "",
        name = this?.name ?: "Unknown Source"
    )
}

fun ApiArticleDto.toDomainArticle(): NewsArticle {
    return NewsArticle(
        source = this.source.toDomainSource(),
        author = this.author ?: "Unknown",
        title = this.title ?: "No Title",
        description = this.description ?: "",
        url = this.url ?: "",
        urlToImage = this.urlToImage ?: "",
        publishedAt = this.publishedAt ?: "",
        content = this.content ?: ""
    )
}

fun List<ApiArticleDto>?.toDomainArticleList(): List<NewsArticle> {
    return this?.mapNotNull { it.toDomainArticle() } ?: emptyList()
}