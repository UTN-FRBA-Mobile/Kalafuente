package com.example.quecomohoy.data.model.recipe

import com.example.quecomohoy.data.model.Ingredient

data class Recipe(
    val id: Int,
    val name: String,
    val picturePath: String,
    val authorName: String,
    val isFavourite: Boolean = false,
    val ingredients : List<Ingredient>? = listOf(),
    val steps : List<String>? = listOf()
)
