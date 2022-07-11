package com.example.quecomohoy.data.model.recipe

import com.example.quecomohoy.data.model.Ingredient
import java.io.Serializable

data class Recipe(
    val id: Int,
    val name: String,
    val picture: String,
    val authorName: String,
    var isFavourite: Boolean = false,
    val ingredients : List<Ingredient> = listOf(),
    val steps : List<String> = listOf()
) : Serializable
