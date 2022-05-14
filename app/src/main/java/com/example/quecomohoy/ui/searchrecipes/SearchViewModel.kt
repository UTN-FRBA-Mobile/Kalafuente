package com.example.quecomohoy.ui.searchrecipes

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.quecomohoy.data.RecipeRepository
import com.example.quecomohoy.data.model.recipe.Recipe

class SearchViewModel(private val recipeRepository : RecipeRepository) : ViewModel() {

    val recipes = MutableLiveData<List<Recipe>>()

    fun findRecipes(name : String){
        recipes.postValue(recipeRepository.findRecipesByName(name))
    }

}