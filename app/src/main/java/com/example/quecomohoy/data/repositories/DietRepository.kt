package com.example.quecomohoy.data.repositories

import com.example.quecomohoy.data.model.diet.Diet
import com.example.quecomohoy.data.services.diets.DietService

class DietRepository {
    private val api = DietService()

    suspend fun findUserDiet(userId: Int): Diet? {
        return api.findUserDiet(userId)
    }

    suspend fun saveUserDiet(dietId: Int, userId: Int) {
        api.saveUserDiet(dietId, userId)
    }
}