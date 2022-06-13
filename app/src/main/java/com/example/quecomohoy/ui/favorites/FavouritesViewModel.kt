package com.example.quecomohoy.ui.favorites

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
}