package com.example.quecomohoy.data.services.user

import com.example.quecomohoy.data.model.Recommendation
import com.example.quecomohoy.data.model.user.User
import com.example.quecomohoy.data.services.RetrofitFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserService {
    private val retrofit = RetrofitFactory.getRetrofit()

    suspend fun getRecommendationsByUserId(id: Int): List<Recommendation> {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(UserApiClient::class.java).getRecommendationsByUserId(id)
            response.body()?: emptyList()
        }
    }

    suspend fun loginUser(username: String, password: String): User?{
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(UserApiClient::class.java).login(username, password)
            response.body()
        }
    }
}