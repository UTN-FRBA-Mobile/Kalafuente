package com.example.quecomohoy.data

import com.example.quecomohoy.data.model.Ingredient

class IngredientRepository {

    private var ingredients : List<Ingredient> = listOf(
        Ingredient(1, "Manzana", "https://www.cuerpomente.com/medio/2020/11/10/manzana_a1c5bdb0_1200x1200.jpg"),
        Ingredient(2, "Pollo", "https://www.carnave.com.ar/wp-content/uploads/2020/05/Pollo-entero.jpg"),
        Ingredient(3, "Huevo", "https://www.collinsdictionary.com/images/full/eggs_110803370_1000.jpg"),
        Ingredient(4, "Mostaza","https://cdn2.cocinadelirante.com/sites/default/files/styles/gallerie/public/images/2017/04/most.jpg"),
        Ingredient(5, "Fideos", "https://jumboargentina.vtexassets.com/arquivos/ids/209822/Fideo-Molto-Guiseros-Fideos-Guisero-Molto-500-Gr-1-46224.jpg?v=636383732923400000")
    )

    fun findByName(name : String) : List<Ingredient>{
        if(name.isEmpty()){
            return listOf()
        }
        return ingredients.filter { it.name.contains(name, true) }
    }

}