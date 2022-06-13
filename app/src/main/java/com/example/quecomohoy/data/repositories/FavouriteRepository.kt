package com.example.quecomohoy.data.repositories

import com.example.quecomohoy.data.model.recipe.Recipe
import com.example.quecomohoy.data.services.favourites.FavouriteService

class FavouriteRepository {

    private val api = FavouriteService()

    suspend fun saveFavourite(recipe: Recipe, userId : Int) {
         api.postFavourite(recipe.id, userId)
    }

    suspend fun deleteFavourite(recipe: Recipe, userId: Int){
        api.deleteFavourite(recipe.id, userId)
    }

    suspend fun getFavouritesByUserId(userId: Int): List<Recipe>{
        return api.getFavourites(userId)
    }
}