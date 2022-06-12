package com.example.quecomohoy.data.model

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
data class LoggedInUser(
    val username: String,
    val displayName: String,
    val image: String
)