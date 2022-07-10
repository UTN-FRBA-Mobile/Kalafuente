package com.example.quecomohoy.data.services.diets

import com.example.quecomohoy.data.model.diet.Diet
import com.example.quecomohoy.data.services.RetrofitFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DietService {
    private val retrofit = RetrofitFactory.getRetrofit()

    suspend fun findUserDiet(userId: Int): Diet? {
        return withContext(Dispatchers.IO){
            val response = retrofit.create(DietApiClient::class.java).getUserDiet(userId)
            response.body()
        }
    }

    suspend fun saveUserDiet(dietId: Int, userId: Int) {
        withContext(Dispatchers.IO){
            retrofit.create(DietApiClient::class.java).saveUserDiet(dietId, userId)
        }
    }

}