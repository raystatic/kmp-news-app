package org.raystatic.newsapp

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.ktor.client.HttpClient
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import newsapp.composeapp.generated.resources.Res
import newsapp.composeapp.generated.resources.compose_multiplatform
import org.raystatic.newsapp.networking.NewsClient
import org.raystatic.newsapp.presentation.screen.NewsListScreen
import org.raystatic.newsapp.util.onError
import org.raystatic.newsapp.util.onSuccess

@Composable
@Preview
fun App() {
    MaterialTheme {
        NewsListScreen(
            onArticleClick = { article ->
                // TODO: Handle article click
                // For now, let's just print the article title
                println("Article clicked: ${article.title}")
                // Later, you might navigate to a detail screen or open in a browser
            }
        )
    }
}