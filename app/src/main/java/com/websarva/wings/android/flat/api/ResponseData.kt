package com.websarva.wings.android.flat.api

import com.squareup.moshi.Json

class ResponseData {

    data class ResponsePost(
        @Json(name = "message") val message: String
    )

    data class ResponseGetUser(
        @Json(name = "id") val id: Int,
        @Json(name = "name") val name: String,
        @Json(name = "status") val status: Int,
        @Json(name = "spot") val spot: String?,
        @Json(name = "icon_path") val icon_path: String,
        @Json(name = "loggedin_at") val logged_in_at: String?
    )

    data class ResponseGetFriends(
        @Json(name = "mutual") val mutual: List<Mutual>,
        @Json(name = "one_side") val one_side: List<OneSide>
    )

    data class Mutual(
        @Json(name = "id") val id: Int,
        @Json(name = "name") val name: String,
        @Json(name = "status") val status: Int,
        @Json(name = "spot") val spot: String,
        @Json(name = "icon_path") val icon_path: String,
        @Json(name = "loggedin_at") val logged_in_at: String?
    )

    data class OneSide(
        @Json(name = "id") val id: Int,
        @Json(name = "name") val name: String,
        @Json(name = "status") val status: Int,
        @Json(name = "spot") val spot: String,
        @Json(name = "icon_path") val icon_path: String,
        @Json(name = "loggedin_at") val logged_in_at: String?
    )

    data class ResponseSearchUser(
        @Json(name = "id") val id: Int,
        @Json(name = "name") val name: String,
        @Json(name = "icon_path") val icon_path: String,
        @Json(name = "applied") var applied: Boolean,
        @Json(name = "requested") val requested: Boolean
    )

}