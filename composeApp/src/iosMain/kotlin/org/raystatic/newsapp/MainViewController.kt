package org.raystatic.newsapp

import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import io.ktor.client.engine.darwin.Darwin
import org.raystatic.newsapp.networking.NewsClient
import org.raystatic.newsapp.networking.createHttpClient

fun MainViewController() = ComposeUIViewController {
    App(
        client = remember {
            NewsClient(
                createHttpClient(
                    Darwin.create()
                )
            )
        }
    )
}