package com.websarva.wings.android.flat.api

import com.squareup.moshi.Json

class ResponseData {
    data class ResponseAddFriend(
        @Json(name = "message") val message: String
    )

    data class ResponseGetMe(
        @Json(name = "id") val id: String,
        @Json(name = "name") val name: String,
        @Json(name = "status") val status: Int,
        @Json(name = "beacon") val beacon: String,
        @Json(name = "icon") val icon: String
    )
}