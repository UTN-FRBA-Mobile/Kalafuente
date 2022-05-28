package com.example.quecomohoy.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quecomohoy.data.model.Ingredient
import com.example.quecomohoy.data.repositories.IngredientRepository
import kotlinx.coroutines.launch

class IngredientViewModel(private val ingredientRepository: IngredientRepository) : ViewModel() {

    val ingredients = MutableLiveData<List<Ingredient>>()
    val selectedIngredients = listOf<Ingredient>().toMutableList()
        get() = field

    val addedIngredient = MutableLiveData<Ingredient>()
    val isSearching = MutableLiveData<Boolean>()

    fun getIngredientsByName(name : String){
        isSearching.postValue(name.isNotEmpty())
        viewModelScope.launch {
            ingredients.postValue(ingredientRepository.getIngredientsByName(name))
        }
    }

    fun addIngredient(ingredient : Ingredient){
        selectedIngredients.add(ingredient)
        addedIngredient.postValue(ingredient)
    }

    fun removeIngredient(index : Int){
        selectedIngredients.removeAt(index)
    }

    fun hasSelectedIngredients(): Boolean {
        return selectedIngredients.isNotEmpty()
    }

    fun getSelectedIngredientsIds(): IntArray {
        return selectedIngredients.map { it.id }.toIntArray()
    }

}