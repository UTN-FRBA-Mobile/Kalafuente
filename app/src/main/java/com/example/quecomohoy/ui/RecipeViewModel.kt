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
    val recipes = MutableLiveData<Resource<List<Recipe>>>()
    val recipe = MutableLiveData<Resource<Recipe>>()

    fun getRecipesByName(name : String){
        recipes.postValue(Resource.loading(mapOf("searchTerm" to name)))
        viewModelScope.launch {
            try {
                val recipesByName = recipeRepository.getRecipesByName(name)
                if (recipesByName.isNotEmpty()){
                    recipes.postValue(Resource.success(recipesByName, mapOf("searchTerm" to name)))
                }
            } catch (e : Exception){
                recipes.postValue(Resource.error("", null, null))
            }
        }
    }

    fun getRecipeById(id : Int){
        viewModelScope.launch {
            try {
                recipe.postValue(Resource.success(recipeRepository.getRecipeById(id), null))
            } catch (e : Exception){
                recipe.postValue(Resource.error("", null, null))
            }
        }
    }

    fun getRecipesByIngredients(ingredientIds: List<Int>) {
        viewModelScope.launch {
            val recipesByIngredients = recipeRepository.getRecipesByIngredients(ingredientIds)
            recipes.postValue(Resource.success(recipesByIngredients, null))
        }
    }


    fun cleanRecipes() {
        recipes.postValue(Resource.success(emptyList(), null))
    }

}