package com.example.quecomohoy.ui.listeners

interface RecipeListener {
    fun onMarkAsFavourite(recipeId: Int, marked: Boolean)

    fun onClickRecipe(recipeId: Int)
}