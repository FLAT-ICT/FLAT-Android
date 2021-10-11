package com.websarva.wings.android.flat.api

import com.squareup.moshi.Json

class PostData {
    data class PostAddFriend(
        @Json(name = "my_id") val my_id: String,
        @Json(name = "target_id") val opponents_id: String
    )
}