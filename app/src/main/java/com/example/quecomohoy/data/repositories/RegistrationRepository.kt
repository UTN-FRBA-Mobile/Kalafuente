package com.example.quecomohoy.data.repositories

import com.example.quecomohoy.data.model.user.User
import com.example.quecomohoy.data.requests.UserSignupRequest
import com.example.quecomohoy.data.services.registration.RegistrationService

class RegistrationRepository {
    val api = RegistrationService()

    suspend fun signup(user : UserSignupRequest): User? {
        return api.signup(user)
    }
}