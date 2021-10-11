package com.websarva.wings.android.flat.repository

import com.websarva.wings.android.flat.api.PostData
import com.websarva.wings.android.flat.api.ResponseData
import com.websarva.wings.android.flat.api.RestApi
import com.websarva.wings.android.flat.api.Params.Companion.BASE_URL
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class ApiRepository {
    private val retrofit = Retrofit.Builder().apply {
        baseUrl(BASE_URL)
        addConverterFactory(MoshiConverterFactory.create())
    }.build()

    private val service: RestApi = retrofit.create(RestApi::class.java)

    // APIにリクエストしてレスポンスを受け取る
    suspend fun getUser(id: String): Call<ResponseData.ResponseGetUser> {
        return service.getUser(id)
    }

    suspend fun postAddFriend(postData: PostData.PostAddFriend): Call<ResponseData.ResponseAddFriend> {
        return service.postAddFriend(postData)
    }

    companion object Factory {
        val instance: ApiRepository
            @Synchronized get() {
                return ApiRepository()
            }
    }
}