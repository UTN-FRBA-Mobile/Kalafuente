package com.example.quecomohoy.data.services.favourites

import com.example.quecomohoy.data.model.recipe.Recipe
import okhttp3.Response
import retrofit2.http.*

interface FavouriteApiClient {

    @GET("/user/{id}/favourites")
    suspend fun getFavourites(userId : Int) : Result<List<Recipe>>

    @PUT("/user/{id}/favourites")
    suspend fun postFavourite(@Field("recipeId") recipeId: Int, @Path("id") userId: Int) : Response

    @DELETE("/user/{userId}/favourites/{recipeId}")
    suspend fun deleteFavourite(@Path("recipeId") recipeId: Int, @Path("userId") userId: Int) : Response
}