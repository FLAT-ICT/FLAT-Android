package com.websarva.wings.android.flat.api

import com.squareup.moshi.Json

class ResponseData {
    data class ResponseAddFriend(
        @Json(name = "message") val message: String
    )

    data class ResponseGetUser(
        @Json(name = "id") val id: String,
        @Json(name = "name") val name: String,
        @Json(name = "status") val status: Int,
        @Json(name = "beacon") val beacon: String?,
        @Json(name = "icon_path") val icon_path: String
    )

    data class ResponseCheckFriend(
        @Json(name = "id") val id: String,
        @Json(name = "name") val name: String,
        @Json(name = "icon_path") val icon_path: String,
        @Json(name = "applied") val applied: Boolean,
        @Json(name = "requested") val requested: Boolean
    )

    data class ResponseGetFriends(
        @Json(name = "mutual") val mutual: List<ResponseGetUser>?,
        @Json(name = "one_side") val one_side: List<ResponseGetUser>?
    )
}