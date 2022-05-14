package com.example.quecomohoy.ui.searchrecipes

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.quecomohoy.data.IngredientRepository
import com.example.quecomohoy.data.RecipeRepository
import com.example.quecomohoy.data.model.Ingredient
import com.example.quecomohoy.data.model.recipe.Recipe

class SearchViewModel(private val recipeRepository : RecipeRepository, private val ingredientRepository: IngredientRepository) : ViewModel() {

    val recipes = MutableLiveData<List<Recipe>>()
    val ingredients = MutableLiveData<List<Ingredient>>()


    fun findRecipes(name : String){
        recipes.postValue(recipeRepository.findRecipesByName(name))
    }

    fun findIngredients(name : String){
        ingredients.postValue(ingredientRepository.findByName(name))
    }

}