package org.raystatic.newsapp

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import okhttp3.OkHttp
import org.raystatic.newsapp.networking.NewsClient
import org.raystatic.newsapp.networking.createHttpClient

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "NewsApp",
    ) {
        App(
            client = NewsClient(
                createHttpClient(
                    engine = io.ktor.client.engine.okhttp.OkHttp.create()
                )
            )
        )
    }
}