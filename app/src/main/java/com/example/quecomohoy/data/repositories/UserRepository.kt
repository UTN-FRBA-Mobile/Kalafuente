package com.example.quecomohoy.data.repositories

import com.example.quecomohoy.data.model.Recommendation
import com.example.quecomohoy.data.services.user.UserService
import com.example.quecomohoy.ui.login.LoginResult

class UserRepository {
    private val api = UserService()
    suspend fun login(username: String, password: String): LoginResult {
        return api.getRecommendationsByUserId(id)
    }
}