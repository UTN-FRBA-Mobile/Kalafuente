package com.example.quecomohoy.data.repositories

import com.example.quecomohoy.data.model.recipe.Recipe
import com.example.quecomohoy.data.services.recipes.RecipeService
import com.example.quecomohoy.data.services.recipes.RecipesApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RecipeRepository {
    private val api = RecipeService()

    suspend fun getRecipesByName(name: String): List<Recipe> {
        if(name.isEmpty()){
            return listOf()
        }
        return api.getRecipesByName(name)
    }

    suspend fun getRecipesByIngredients(ingredientIds : List<Int>) : List<Recipe>{
        return api.getRecipesByIngredients(ingredientIds)
    }

}