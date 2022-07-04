package com.example.quecomohoy.data.model.diet

const val OMNIVORE_ID = 1
const val VEGAN_ID = 2
const val VEGETARIAN = 3
const val PESCETARIAN = 4
const val OTHER = 5


data class Diet
    (
        var id: Int,
        var name : String,
        var descrition : String,
    )
