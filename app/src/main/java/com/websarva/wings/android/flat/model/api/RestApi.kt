package com.websarva.wings.android.flat.model.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import com.websarva.wings.android.flat.model.api.PostData.PostAddFriend
import com.websarva.wings.android.flat.model.api.ResponseData.ResponseAddFriend

interface RestApi {

    @POST("v1/friends/add")
    fun postAddFriend(@Body postAddFriend: PostAddFriend): Call<ResponseAddFriend>
}