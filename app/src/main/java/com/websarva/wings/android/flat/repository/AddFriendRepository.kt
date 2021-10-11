package com.websarva.wings.android.flat.repository

import com.websarva.wings.android.flat.api.PostData
import com.websarva.wings.android.flat.api.ResponseData
import com.websarva.wings.android.flat.api.RestApi
import com.websarva.wings.android.flat.api.Params.Companion.BASE_URL
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class AddFriendRepository {
    private val retrofit = Retrofit.Builder().apply {
        baseUrl(BASE_URL)
        addConverterFactory(MoshiConverterFactory.create())
    }.build()

    private val addFriendService: RestApi = retrofit.create(RestApi::class.java)

    // APIにリクエストしてレスポンスを受け取る
    fun getMeRepos(id: String): Call<ResponseData.ResponseGetMe> {
        return addFriendService.getMe(id)
    }

    fun postAddFriend(postData: PostData.PostAddFriend): Call<ResponseData.ResponseAddFriend> {
        return addFriendService.postAddFriend(postData)
    }
}