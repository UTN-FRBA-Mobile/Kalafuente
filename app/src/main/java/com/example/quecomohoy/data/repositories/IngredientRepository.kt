package com.example.quecomohoy.data.repositories

import com.example.quecomohoy.data.model.Ingredient
import com.example.quecomohoy.data.services.ingredients.IngredientService

class IngredientRepository {

    private val api = IngredientService()

    suspend fun getIngredientsByName(name: String): List<Ingredient> {
        if (name.isEmpty()) {
            return listOf()
        }
        return api.getIngredientsByName(name)
    }
}