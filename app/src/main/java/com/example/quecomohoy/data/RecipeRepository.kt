package com.example.quecomohoy.data

import com.example.quecomohoy.data.model.recipe.Recipe

class RecipeRepository {

    private var recipes : List<Recipe> = listOf(
        Recipe(1, "Pollo al limón", "https://www.paulinacocina.net/wp-content/uploads/2021/11/pollo-asado.jpg" ),
        Recipe(2, "Hamburguesa con lechuga", "https://upload.wikimedia.org/wikipedia/commons/thumb/6/62/NCI_Visuals_Food_Hamburger.jpg/640px-NCI_Visuals_Food_Hamburger.jpg")
    )

    fun findRecipesByName(name : String) : List<Recipe> {
        if(name.isEmpty()){
            return listOf()
        }
        return recipes.filter { recipe -> recipe.name.contains(name, true) }
    }
}