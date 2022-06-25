package com.example.quecomohoy.ui.favorites

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quecomohoy.data.model.recipe.Recipe
import com.example.quecomohoy.data.repositories.FavouriteRepository
import com.example.quecomohoy.ui.Resource
import kotlinx.coroutines.launch

class FavouritesViewModel (private val favouriteRepository: FavouriteRepository) : ViewModel()  {
    val favourites = MutableLiveData<Resource<List<Recipe>>>()

    fun getFavouritesByUser(id : Int){
        viewModelScope.launch {
            try {
                val results = favouriteRepository.getFavouritesByUserId(id)
                favourites.postValue(Resource.success(results, null))
            }catch (e : Exception){
                favourites.postValue(Resource.error("", null, null))
            }
        }
    }

    fun markAsFavourite(recipeId : Int, userId : Int, mark: Boolean){
        viewModelScope.launch {
            try {
                if(mark){
                    favouriteRepository.markAsFavourite(recipeId, userId)
                } else{
                    favouriteRepository.deleteFavourite(recipeId, userId)
                }
            } catch (e : Exception){
                Log.e(TAG, "Error", e)
            }
        }
    }
}