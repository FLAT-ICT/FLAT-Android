package com.websarva.wings.android.flat.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import com.websarva.wings.android.flat.api.PostData.PostAddFriend
import com.websarva.wings.android.flat.api.ResponseData.ResponseAddFriend
import com.websarva.wings.android.flat.api.ResponseData.ResponseGetMe
import retrofit2.http.GET
import retrofit2.http.Query

interface RestApi {

    @GET("user")
    fun getMe(@Query("id") id: String): Call<ResponseGetMe>

    @POST("friends/add")
    fun postAddFriend(@Body postAddFriend: PostAddFriend): Call<ResponseAddFriend>
}