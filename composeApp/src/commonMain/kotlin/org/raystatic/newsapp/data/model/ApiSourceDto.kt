package org.raystatic.newsapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ApiSourceDto(
    val id: String?,
    val name: String? // Making name nullable to be safe, though JSON shows it populated
)