package com.example.quecomohoy.data.services.ingredients

import com.example.quecomohoy.data.model.Ingredient
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface IngredientApiClient {

    @GET("/ingredients")
    suspend fun getIngredientsByName(@Query("name")name : String) : Response<List<Ingredient>>

}