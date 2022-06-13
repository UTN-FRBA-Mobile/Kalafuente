package com.example.quecomohoy.data.services.registration

import com.example.quecomohoy.data.model.user.User
import com.example.quecomohoy.data.requests.UserSignupRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RegistrationApi {
    @POST("/signup")
    suspend fun signup(
      @Body  user : UserSignupRequest
    ): Response<User>
}