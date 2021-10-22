package com.websarva.wings.android.flat.api

import retrofit2.http.Body
import retrofit2.http.POST
import com.websarva.wings.android.flat.api.PostData.PostFriends
import com.websarva.wings.android.flat.api.ResponseData.ResponsePost
import com.websarva.wings.android.flat.api.ResponseData.ResponseGetUser
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RestApi {

    // このidはtarget_id
    @GET("/v1/user")
    suspend fun getUser(@Query("id") id: Int): Response<ResponseGetUser>

    @GET("/v1/user/search")
    suspend fun searchUsers(@Query("my_id") my_id: Int, @Query("target_name") target_name: String): Response<List<ResponseData.ResponseSearchUsers>>

    @GET("/v1/friends")
    suspend fun getFriends(@Query("my_id") my_id: Int): Response<ResponseData.ResponseGetFriends>

    @POST("/v1/friends/add")
    suspend fun postAddFriend(@Body postFriends: PostFriends): Response<ResponsePost>

    @POST("/v1/friends/reject")
    suspend fun postRejectFriend(@Body postFriends: PostFriends): Response<ResponsePost>
}