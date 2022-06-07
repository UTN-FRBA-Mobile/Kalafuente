package com.example.quecomohoy.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class RecipeViewModelFactory: ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RecipeViewModel(com.example.quecomohoy.data.repositories.RecipeRepository()) as T
    }

}