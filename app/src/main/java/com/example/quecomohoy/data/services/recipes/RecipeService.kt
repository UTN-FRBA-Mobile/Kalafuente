package com.example.quecomohoy.data.services.recipes

import com.example.quecomohoy.data.model.recipe.Recipe
import com.example.quecomohoy.data.services.RetrofitFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RecipeService {

    private val retrofit = RetrofitFactory.getRetrofit()

    suspend fun getRecipesByName(name: String): List<Recipe> {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(RecipesApiClient::class.java).getRecipesByName(name)
            response.body()?: emptyList()
        }
    }

    suspend fun getRecipesByIngredients(ingredientIds : List<Int>) : List<Recipe>{
        return withContext(Dispatchers.IO){
            val response = retrofit.create(RecipesApiClient::class.java).getRecipesByIngredientIds(ingredientIds)
            response.body()?: emptyList()
        }
    }

    suspend fun getRecipeById(id : Int): Recipe?{
        return withContext(Dispatchers.IO){
            val respose = retrofit.create(RecipesApiClient::class.java).getRecipeById(id)
            respose.body()
        }
    }

}