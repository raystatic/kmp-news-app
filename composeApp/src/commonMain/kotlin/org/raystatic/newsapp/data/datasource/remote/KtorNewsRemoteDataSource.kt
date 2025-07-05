package org.raystatic.newsapp.data.datasource.remote

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.raystatic.newsapp.data.model.NewsApiResponseDto
import org.raystatic.newsapp.util.Result


private const val API_KEY = "1199abc09655435b829cac50f16f9265"
private const val BASE_URL = "newsapi.org"
private const val API_VERSION = "/v2/"

class KtorNewsRemoteDataSource : NewsRemoteDataSource {

    private val client = HttpClient {
        expectSuccess = true // Ktor will throw exceptions for non-2xx responses by default
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
                useAlternativeNames = false
            })
        }
    }

    override suspend fun fetchNews(
        category: String,
        page: Int
    ): Result<NewsApiResponseDto, Exception> {
        return try {
            val response: NewsApiResponseDto = client.get {
                url {
                    protocol = URLProtocol.HTTPS
                    host = BASE_URL
                    path("${API_VERSION}top-headlines")
                    parameters.append("apiKey", API_KEY)
                    parameters.append("category", category)
                    parameters.append("page", page.toString())
                    parameters.append("pageSize", "20")
                    parameters.append("language", "en")
                }
            }.body()
            Result.Success(response)
        } catch (e: Exception) {
            println("Network request failed: ${e.message}")
            Result.Error(e)
        }
    }
}