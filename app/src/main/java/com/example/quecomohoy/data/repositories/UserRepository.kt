package com.example.quecomohoy.data.repositories

import com.example.quecomohoy.data.model.perfil.UserPreferences
import com.example.quecomohoy.data.services.user.UserService

class UserRepository {
    private val api = UserService()
    suspend fun getPreferences(id: Int): UserPreferences? {
        return api.getPreferences(id)
    }
}