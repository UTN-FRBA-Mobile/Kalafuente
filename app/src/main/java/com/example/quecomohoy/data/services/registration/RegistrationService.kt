package com.example.quecomohoy.data.services.registration

import com.example.quecomohoy.data.SignupException
import com.example.quecomohoy.data.model.user.User
import com.example.quecomohoy.data.requests.UserSignupRequest
import com.example.quecomohoy.data.services.RetrofitFactory
import com.google.gson.Gson


class RegistrationService {
    private val retrofit = RetrofitFactory.getRetrofit()

    suspend fun signup(user: UserSignupRequest): User? {
        val response = retrofit.create(RegistrationApi::class.java).signup(user)
        if(!response.isSuccessful){
            throw Gson().fromJson(response.errorBody()?.charStream(), SignupException::class.java)
        }
        return response.body()
    }
}