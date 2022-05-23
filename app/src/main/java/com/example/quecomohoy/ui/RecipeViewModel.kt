package com.example.quecomohoy.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quecomohoy.data.model.recipe.Recipe
import com.example.quecomohoy.data.repositories.RecipeRepository
import kotlinx.coroutines.launch

class RecipeViewModel(private val recipeRepository : RecipeRepository) :
    ViewModel()
{
    val recipes = MutableLiveData<List<Recipe>>()

    fun getRecipesByName(name : String){
        viewModelScope.launch {
            recipes.postValue(recipeRepository.getRecipesByName(name))
        }
    }

}