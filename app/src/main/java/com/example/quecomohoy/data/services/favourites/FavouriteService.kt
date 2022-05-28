package com.example.quecomohoy.data.services.favourites

import com.example.quecomohoy.data.services.RetrofitFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FavouriteService {

    private val retrofit = RetrofitFactory.getRetrofit()

    suspend fun postFavourite(recipeId : Int, userId : Int) {
        return withContext(Dispatchers.IO) {
            retrofit.create(FavouriteApiClient::class.java).postFavourite(recipeId, userId)
        }
    }

    suspend fun deleteFavourite(recipeId: Int, userId: Int){
        return withContext(Dispatchers.IO) {
            retrofit.create(FavouriteApiClient::class.java).deleteFavourite(recipeId, userId)
        }
    }

}