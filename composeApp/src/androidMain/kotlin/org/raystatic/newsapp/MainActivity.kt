package org.raystatic.newsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import io.ktor.client.engine.okhttp.OkHttp
import org.raystatic.newsapp.networking.NewsClient
import org.raystatic.newsapp.networking.createHttpClient

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            App(NewsClient(
                createHttpClient(OkHttp.create())
            ))
        }
    }
}