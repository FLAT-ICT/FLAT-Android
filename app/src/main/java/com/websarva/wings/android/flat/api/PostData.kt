package com.websarva.wings.android.flat.api

import com.squareup.moshi.Json

class PostData {
    data class PostFriends(
        @Json(name = "my_id") val my_id: Int,
        @Json(name = "target_id") val target_id: Int
    )
}