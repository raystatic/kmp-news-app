package org.raystatic.newsapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform