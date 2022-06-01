package com.example.quecomohoy.data.services.ingredients

import com.example.quecomohoy.data.model.Ingredient
import com.example.quecomohoy.data.services.RetrofitFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class IngredientService {

    private val retrofit = RetrofitFactory.getRetrofit()

    suspend fun getIngredientsByName(name: String): List<Ingredient> {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(IngredientApiClient::class.java).getIngredientsByName(name)
            response.body()?: emptyList()
        }
    }

}