package com.websarva.wings.android.flat.repository

import androidx.room.Update
import com.websarva.wings.android.flat.api.PostData
import com.websarva.wings.android.flat.api.ResponseData
import com.websarva.wings.android.flat.api.RestApi
import com.websarva.wings.android.flat.api.Params.Companion.BASE_URL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
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
    suspend fun getUser(target_id: Int): Response<ResponseData.ResponseGetUser> =
        withContext(IO){service.getUser(target_id)}

    suspend fun searchUsers(my_id: Int, target_name: String): Response<List<ResponseData.ResponseSearchUser>> =
        withContext(IO){service.searchUsers(my_id, target_name)}

    suspend fun getFriends(my_id: Int): Response<ResponseData.ResponseGetFriends> =
        withContext(IO){service.getFriends(my_id)}

    suspend fun postAddFriend(postData: PostData.PostFriends): Response<ResponseData.ResponsePost> =
        withContext(IO){service.postAddFriend(postData)}

    suspend fun postRejectFriend(postData: PostData.PostFriends): Response<ResponseData.ResponsePost> =
        withContext(IO){service.postRejectFriend(postData)}

    suspend fun postBeacon(postData: PostData.PostBeacon): Response<ResponseData.ResponsePost> =
        withContext(IO){service.postBeacon(postData)}

    suspend fun postRegister(postData: PostData.RegisterData): Response<ResponseData.ResponseGetUser> =
        withContext(IO){service.postRegister(postData)}

    suspend fun postPreLogin(postData: PostData.PostPreLogin): Response<ResponseData.ResponsePreLogin> =
        withContext(IO){service.postPreLogin(postData)}

    suspend fun postLogin(postData: PostData.RegisterData): Response<ResponseData.ResponseGetUser> =
        withContext(IO){service.postLogin(postData)}

    suspend fun postLogout(postData: PostData.PostLogout): Response<ResponseData.ResponsePost> =
        withContext(IO){service.postLogout(postData)}

    suspend fun updateName(postData: PostData.UpdateName): Response<ResponseData.ResponseGetUser> =
        withContext(IO){service.updateName(postData)}

    suspend fun updateStatus(postData: PostData.UpdateStatus): Response<ResponseData.ResponseGetUser> =
        withContext(IO){service.updateStatus(postData)}

    companion object Factory {
        val instance: ApiRepository
            @Synchronized get() {
                return ApiRepository()
            }
    }
}