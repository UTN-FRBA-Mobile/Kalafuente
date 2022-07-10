package com.example.quecomohoy.data.model.perfil

import com.example.quecomohoy.data.model.Ingredient
import com.example.quecomohoy.data.model.diet.Diet

data class UserPreferences(
    val diet : Diet,
    val likedIngredients : List<Ingredient>,
    val unlikedIngredients : List<Ingredient>
)