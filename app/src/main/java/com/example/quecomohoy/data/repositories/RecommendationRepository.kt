package com.example.quecomohoy.data.repositories

import com.example.quecomohoy.data.model.Recommendation
import com.example.quecomohoy.data.services.user.UserService

class RecommendationRepository {
    private val api = UserService()
    suspend fun getRecommendationsByUser(id: Int): List<Recommendation> {
        return api.getRecommendationsByUserId(id)
    }
}