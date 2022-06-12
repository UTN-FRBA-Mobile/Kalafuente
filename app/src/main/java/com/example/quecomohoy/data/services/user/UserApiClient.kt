package com.example.quecomohoy.data.services.user

import com.example.quecomohoy.data.model.Recommendation
import com.example.quecomohoy.data.model.user.User
import retrofit2.Response
import retrofit2.http.*


interface UserApiClient {
    @GET("/user/{id}/recommendations")
    suspend fun getRecommendationsByUserId(@Path("id")id: Int): Response<List<Recommendation>>
    @POST("/login")
    @FormUrlEncoded
    suspend fun login(
        @Field("userName") username: String,
        @Field("password") password: String,
    ): Response<User>
}