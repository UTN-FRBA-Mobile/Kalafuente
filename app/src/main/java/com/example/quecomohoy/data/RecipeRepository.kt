package com.example.quecomohoy.data

import com.example.quecomohoy.data.model.recipe.Recipe

class RecipeRepository {

    private var recipes : List<Recipe> = listOf(
        Recipe(1, "Pollo al limón", "https://www.paulinacocina.net/wp-content/uploads/2021/11/pollo-asado.jpg" , "Bruno Cobos"),
        Recipe(2, "Hamburguesa con lechuga", "https://upload.wikimedia.org/wikipedia/commons/thumb/6/62/NCI_Visuals_Food_Hamburger.jpg/640px-NCI_Visuals_Food_Hamburger.jpg", "Bruno Cobos"),
        Recipe(3,"Omelette con ensalada de cherrys y hongos", "https://viapais.com.ar/resizer/mUQiFA14EV_X7bln_vY2CaTJ6V4=/982x551/smart/cloudfront-us-east-1.images.arcpublishing.com/grupoclarin/GIYWKYLGGVRTQNBYHA3TCOBXGU.jpg", "pipo89"),
        Recipe(4,"Espinacas a la crema", "https://dam.cocinafacil.com.mx/wp-content/uploads/2019/03/espinacas-a-la-crema.png", "maria_abc"),
        Recipe(5,"Pancito con queso super crocante", "https://i0.wp.com/blog.marianlaquecocina.com/wp-content/uploads/2018/04/20180416_144118.jpg", "deLaNonna")
    )

    fun findRecipesByName(name : String) : List<Recipe> {
        if(name.isEmpty()){
            return listOf()
        }
        return recipes.filter { recipe -> recipe.name.contains(name, true) }
    }

}