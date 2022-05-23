package com.example.quecomohoy.data.repositories

import com.example.quecomohoy.data.model.recipe.Recipe
import com.example.quecomohoy.data.services.recipes.RecipeService

class RecipeRepository {
    private val api = RecipeService()
    suspend fun getRecipesByName(name: String): List<Recipe> {
        if(name.isEmpty()){
            return listOf()
        }
        return api.getRecipesByName(name)
    }
}