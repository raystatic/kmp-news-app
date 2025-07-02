package org.raystatic.newsapp.networking.response

import kotlinx.serialization.Serializable

@Serializable
data class Source(
    val id: String?=null,
    val name: String?=null
)