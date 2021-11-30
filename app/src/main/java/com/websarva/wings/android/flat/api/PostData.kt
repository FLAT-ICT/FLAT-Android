package com.websarva.wings.android.flat.api

import com.squareup.moshi.Json

class PostData {
    data class PostFriends(
        @Json(name = "my_id") val my_id: Int,
        @Json(name = "target_id") val target_id: Int
    )

    data class PostBeacon(
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
}