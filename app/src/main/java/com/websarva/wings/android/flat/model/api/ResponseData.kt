package com.websarva.wings.android.flat.model.api

import com.squareup.moshi.Json

class ResponseData {
    data class ResponseAddFriend(
        @Json(name = "message") val message: String
    )
}