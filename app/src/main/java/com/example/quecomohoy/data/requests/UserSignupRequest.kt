package com.example.quecomohoy.data.requests

data class UserSignupRequest(
    val name: String,
    val lastName: String,
    val userName: String,
    val email: String,
    val password: String
)
