package com.example.quecomohoy.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quecomohoy.data.model.recipe.Recipe
import com.example.quecomohoy.data.repositories.RecipeRepository
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RecipeViewModel(private val recipeRepository : RecipeRepository) :
    ViewModel()
{
    val recipes = MutableLiveData<List<Recipe>>(emptyList())
    val isSearching = MutableLiveData<Boolean>()

    fun getRecipesByName(name : String){
        isSearching.postValue(name.isNotEmpty())
        viewModelScope.launch {
            recipes.postValue(recipeRepository.getRecipesByName(name))
        }
    }

    fun getRecipesByIngredients(ingredientIds: List<Int>) {
        isSearching.postValue(true)
        viewModelScope.launch {
            recipes.postValue(recipeRepository.getRecipesByIngredients(ingredientIds))
        }
    }

}