package com.example.quecomohoy.data.services.diets

import com.example.quecomohoy.data.model.diet.Diet
import retrofit2.Response
import retrofit2.http.*

interface DietApiClient {

    @GET("user/{id}/diet")
    suspend fun getUserDiet(@Path("id") userId: Int): Response<Diet>

    @PUT("user/{id}/diet")
    @FormUrlEncoded
    suspend fun saveUserDiet(@Path("id") userId: Int, @Field("dietId") dietId: Int ) : Response<Unit>
}