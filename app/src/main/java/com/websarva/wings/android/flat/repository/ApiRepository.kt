package com.websarva.wings.android.flat.repository

import com.websarva.wings.android.flat.api.PostData
import com.websarva.wings.android.flat.api.ResponseData
import com.websarva.wings.android.flat.api.RestApi
import com.websarva.wings.android.flat.api.Params.Companion.BASE_URL
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class ApiRepository {
    // 10秒でタイムアウトとなるように設定
    private val client = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .build()

    private val service: RestApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .client(client)
        .build()
        .create(RestApi::class.java)

    // APIにリクエストしてレスポンスを受け取る
    suspend fun getUser(id: String): Response<ResponseData.ResponseGetUser> =
        withContext(IO){service.getUser(id)}

    suspend fun postAddFriend(postData: PostData.PostAddFriend): Response<ResponseData.ResponseAddFriend> =
        withContext(IO){service.postAddFriend(postData)}

    companion object Factory {
        val instance: ApiRepository
            @Synchronized get() {
                return ApiRepository()
            }
    }
}