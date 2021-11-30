package com.websarva.wings.android.flat.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User(
    @PrimaryKey
    @ColumnInfo(name = "id") val myId: Int,
    val name: String,
    val status: Int,
    val spot: String?,
    @ColumnInfo(name = "icon_path") val iconPath: String,
    @ColumnInfo(name = "logged_in_at") val loggedInAt: String?
)
