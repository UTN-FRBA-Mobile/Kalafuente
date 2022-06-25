package com.example.quecomohoy.data.repositories

import com.example.quecomohoy.data.model.recipe.Recipe
import com.example.quecomohoy.data.services.favourites.FavouriteService

class FavouriteRepository {

    private val api = FavouriteService()

    suspend fun markAsFavourite(recipeId: Int, userId: Int) {
         api.postFavourite(recipeId, userId)
    }

    suspend fun deleteFavourite(recipe: Int, userId: Int){
        api.deleteFavourite(recipe, userId)
    }

    suspend fun getFavouritesByUserId(userId: Int): List<Recipe>{
        return api.getFavourites(userId)
    }
}