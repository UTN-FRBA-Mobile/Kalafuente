package com.example.quecomohoy.data.model.perfil

import com.example.quecomohoy.data.model.Ingredient
import com.example.quecomohoy.data.model.diet.Diet
import java.io.Serializable

data class UserPreferences(
    val userId : Int,
    val diet : Diet,
    var likedIngredients : List<Ingredient>,
    var unlikedIngredients : List<Ingredient>
) : Serializable