package com.example.quecomohoy.ui.login

/**
 * User details post authentication that is exposed to the UI
 */
data class LoggedInUserView(
    val displayName: String,
    val userName: String,
    val image: String,
    val id: Int
)