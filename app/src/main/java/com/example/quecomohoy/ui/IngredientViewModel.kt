package com.example.quecomohoy.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quecomohoy.data.model.Ingredient
import com.example.quecomohoy.data.repositories.IngredientRepository
import kotlinx.coroutines.launch

class IngredientViewModel(private val ingredientRepository: IngredientRepository) : ViewModel() {

    val ingredients = MutableLiveData<Resource<List<Ingredient>>>()
    private val _selectedIngredients = listOf<Ingredient>().toMutableList()

    private val _selectedIngredientsData = MutableLiveData<List<Ingredient>>()
    val selectedIngredients: LiveData<List<Ingredient>> = _selectedIngredientsData

    val addedIngredient = MutableLiveData<Ingredient>()


    fun getIngredientsByName(name: String) {
        val additionalData = mapOf("searchTerm" to name)
        ingredients.postValue(Resource.loading(additionalData))
        viewModelScope.launch {
            try {
                val ingredientsByName = ingredientRepository.getIngredientsByName(name)
                ingredients.postValue(Resource.success(ingredientsByName
                    .filter { !getSelectedIngredientsIds().contains(it.id) }, additionalData))
            } catch (e: Exception) {
                Log.e("IngredientViewModel", e.toString())
                ingredients.postValue(Resource.error("", null, additionalData))
            }
        }
    }

    fun addIngredient(ingredient: Ingredient) {
        _selectedIngredients.add(ingredient)
        _selectedIngredientsData.postValue(_selectedIngredients)
        addedIngredient.postValue(ingredient)
    }

    fun addIngredients(ingredients: List<Ingredient>) {
        _selectedIngredients.addAll(ingredients)
        _selectedIngredientsData.postValue(_selectedIngredients)
    }

    fun removeIngredient(index: Int) {
        _selectedIngredients.removeAt(index)
        _selectedIngredientsData.postValue(_selectedIngredients)
    }

    fun hasSelectedIngredients(): Boolean {
        return !_selectedIngredientsData.value.isNullOrEmpty()
    }

    fun getSelectedIngredientsIds(): IntArray {
        val selectedIngredients = getSelectedIngredients()
        return selectedIngredients.map { it.id }.toIntArray()
    }

    fun getSelectedIngredients(): List<Ingredient> {
        return _selectedIngredientsData.value ?: emptyList()
    }

    fun cleanIngredients() {
        ingredients.postValue(Resource.success(emptyList(), null))
    }

}