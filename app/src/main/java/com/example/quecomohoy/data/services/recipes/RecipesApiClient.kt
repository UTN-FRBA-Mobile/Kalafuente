package com.example.quecomohoy.data.services.recipes

import com.example.quecomohoy.data.model.recipe.Recipe
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RecipesApiClient {
    @GET("/recipes")
    suspend fun getRecipesByName(@Query("name")name : String) : Response<List<Recipe>>

    @GET("/recipes/{id}")
    suspend fun getRecipeById(@Path("id")id: Int): Response<Recipe>

    @GET("/recipes")
    suspend fun getRecipesByIngredientIds(@Query("ingredients=[]")ingredientIds: List<Int>): Response<List<Recipe>>
}