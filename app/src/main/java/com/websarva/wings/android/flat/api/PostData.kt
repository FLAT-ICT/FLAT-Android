package com.websarva.wings.android.flat.api

import com.squareup.moshi.Json

class PostData {
    data class PostFriends(
        @Json(name = "my_id") val my_id: Int,
        @Json(name = "target_id") val target_id: Int
    )

    data class PostBeacon(
        //rssiの値をサーバーに送信しているが、使用はされていない
        @Json(name = "user_id") val user_id: Int,
        @Json(name = "major") val major: Int,
        @Json(name = "minor") val minor: Int,
        @Json(name = "rssi") val rssi: Int
    )

    data class RegisterData(
        @Json(name = "name") val name: String,
        @Json(name = "password") val password: String
    )

    data class PostPreLogin(
        @Json(name = "name") val name: String,
        @Json(name = "password") val password: String?,
        @Json(name = "logged_in_at") val logged_in_at: String?
    )

    data class PostLogout(
        @Json(name = "id") val id: Int
    )

    data class UpdateName(
        @Json(name = "my_id") val my_id: Int,
        @Json(name = "target_name") val target_name: String
    )

    data class UpdateStatus(
        @Json(name = "id") val id: Int,
        @Json(name = "status") val status: Int
    )
}