package com.example.quecomohoy.data.services.favourites

import com.example.quecomohoy.data.model.recipe.Recipe
import retrofit2.http.*
import retrofit2.Response

interface FavouriteApiClient {

    @GET("/user/{id}/favourites")
    suspend fun getFavourites(@Path("id")id: Int) : Response<List<Recipe>>

    @PUT("/user/{id}/favourites")
    suspend fun postFavourite(@Field("recipeId") recipeId: Int, @Path("id") userId: Int)

    @DELETE("/user/{userId}/favourites/{recipeId}")
    suspend fun deleteFavourite(@Path("recipeId") recipeId: Int, @Path("userId") userId: Int)
}