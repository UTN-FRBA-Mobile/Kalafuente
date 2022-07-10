package com.example.quecomohoy.data.model.perfil
const val DIET = 1
const val LIKED_INGREDIENTS = 2
const val UNLIKED_INGREDIENTS = 3
const val MEALTIME = 4

data class UserPreference(val code : Int, val name : String, val value : String?)