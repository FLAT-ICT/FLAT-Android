package com.websarva.wings.android.flat.api

import com.squareup.moshi.Json

class PostData {
    data class PostFriends(
        @Json(name = "my_id") val my_id: Int,
        @Json(name = "target_id") val target_id: Int
    )

    data class PostBeacon(
        @Json(name = "user_id") val user_id: Int,
        @Json(name = "uuid") val uuid: String,
        @Json(name = "major") val major: Int,
        @Json(name = "minor") val minor: Int,
        @Json(name = "rssi") val rssi: Float,
        @Json(name = "distance") val distance: Float
    )

    data class RegisterData(
        @Json(name = "name") val name: String,
        @Json(name = "password") val password: String
    )
}