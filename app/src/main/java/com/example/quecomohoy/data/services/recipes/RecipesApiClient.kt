package com.example.quecomohoy.data.services.recipes

import com.example.quecomohoy.data.model.recipe.Recipe
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface RecipesApiClient {
    @GET("/recipes")
    suspend fun getRecipesByName(@Query("name")name : String) : Response<List<Recipe>>
}