package com.example.quecomohoy.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quecomohoy.data.model.Ingredient
import com.example.quecomohoy.data.repositories.IngredientRepository
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class IngredientViewModel(private val ingredientRepository: IngredientRepository) : ViewModel() {

    val ingredients = MutableLiveData<List<Ingredient>>()
    private val _selectedIngredients = listOf<Ingredient>().toMutableList()

    private val _selectedIngredientsData = MutableLiveData<List<Ingredient>>()

    val addedIngredient = MutableLiveData<Ingredient>()
    val isSearching = MutableLiveData<Boolean>()

    fun getIngredientsByName(name : String){
        isSearching.postValue(name.isNotEmpty())
        viewModelScope.launch {
            ingredients.postValue(ingredientRepository.getIngredientsByName(name))
        }
    }

    fun addIngredient(ingredient : Ingredient){
        _selectedIngredients.add(ingredient)
        _selectedIngredientsData.postValue(_selectedIngredients)
        addedIngredient.postValue(ingredient)
    }

    fun removeIngredient(index : Int){
        _selectedIngredients.removeAt(index)
        _selectedIngredientsData.postValue(_selectedIngredients)
    }

    fun hasSelectedIngredients(): Boolean {
        return !_selectedIngredientsData.value.isNullOrEmpty()
    }

    fun getSelectedIngredientsIds(): IntArray {
        val selectedIngredients =  getSelectedIngredients()
        return selectedIngredients.map { it.id }.toIntArray()
    }

    fun getSelectedIngredients(): List<Ingredient> {
        return _selectedIngredientsData.value?: emptyList()
    }

}