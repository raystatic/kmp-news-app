package org.raystatic.newsapp.networking

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.serialization.SerializationException
import org.raystatic.newsapp.networking.response.TrendingNewsResponse
import org.raystatic.newsapp.util.NetworkError
import org.raystatic.newsapp.util.Result

class NewsClient(
    private val httpClient: HttpClient
) {
    companion object{
        const val API_KEY = "1199abc09655435b829cac50f16f9265"
        const val BASE_URL = "https://newsapi.org/v2/"
    }

    suspend fun fetchNews(category: String, page: Int): Result<TrendingNewsResponse, NetworkError> {
        val response = try {
            httpClient.get(
                urlString = "${BASE_URL}top-headlines"
            ) {
                parameter("category", category)
                parameter("page", page)
                parameter("apiKey", API_KEY)
            }
        } catch(e: UnresolvedAddressException) {
            return Result.Error(NetworkError.NO_INTERNET)
        } catch(e: SerializationException) {
            return Result.Error(NetworkError.SERIALIZATION)
        }

        return when(response.status.value) {
            in 200..299 -> {
                val result = response.body<TrendingNewsResponse>()
                Result.Success(result)
            }
            401 -> Result.Error(NetworkError.UNAUTHORIZED)
            409 -> Result.Error(NetworkError.CONFLICT)
            408 -> Result.Error(NetworkError.REQUEST_TIMEOUT)
            413 -> Result.Error(NetworkError.PAYLOAD_TOO_LARGE)
            in 500..599 -> Result.Error(NetworkError.SERVER_ERROR)
            else -> Result.Error(NetworkError.UNKNOWN)
        }
    }

}