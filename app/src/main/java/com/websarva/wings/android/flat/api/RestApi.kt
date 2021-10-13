package com.websarva.wings.android.flat.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import com.websarva.wings.android.flat.api.PostData.PostAddFriend
import com.websarva.wings.android.flat.api.ResponseData.ResponseAddFriend
import com.websarva.wings.android.flat.api.ResponseData.ResponseGetUser
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RestApi {

    @GET("/v1/user")
    suspend fun getUser(@Query("id") id: String): Response<ResponseGetUser>

    @POST("/v1/friends/add")
    suspend fun postAddFriend(@Body postAddFriend: PostAddFriend): Response<ResponseAddFriend>
}