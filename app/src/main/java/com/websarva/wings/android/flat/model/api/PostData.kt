package com.websarva.wings.android.flat.model.api

import com.squareup.moshi.Json

class PostData {
    data class PostAddFriend(
        @Json(name = "id1") val id1: String,
        @Json(name = "id2") val id2: String
    )
}