package com.example.quecomohoy.data.services.user

import com.example.quecomohoy.data.model.Recommendation
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface UserApiClient {
    @GET("/user/{id}/recommendations")
    suspend fun getRecommendationsByUserId(@Path("id")id: Int): Response<List<Recommendation>>
}