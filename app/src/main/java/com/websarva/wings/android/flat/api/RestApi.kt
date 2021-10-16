package com.websarva.wings.android.flat.api

import retrofit2.http.Body
import retrofit2.http.POST
import com.websarva.wings.android.flat.api.PostData.PostAddFriend
import com.websarva.wings.android.flat.api.ResponseData.ResponseAddFriend
import com.websarva.wings.android.flat.api.ResponseData.ResponseGetUser
import com.websarva.wings.android.flat.api.ResponseData.ResponseCheckFriend
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RestApi {

    // このidはtarget_id
    @GET("/v1/user")
    suspend fun getUser(@Query("id") id: String): Response<ResponseGetUser>

    @GET("/v1/user/check")
    suspend fun checkFriend(@Query("my_id") my_id: String, @Query("target_id") target_id: String): Response<ResponseCheckFriend>

    // このidはmy_id
    @GET("/v1/friends")
    suspend fun getFriends(@Query("id") id: String): Response<ResponseData.ResponseGetFriends>

    @POST("/v1/friends/add")
    suspend fun postAddFriend(@Body postAddFriend: PostAddFriend): Response<ResponseAddFriend>
}