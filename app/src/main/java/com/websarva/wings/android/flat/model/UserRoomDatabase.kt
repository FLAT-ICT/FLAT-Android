package com.websarva.wings.android.flat.model

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(User::class), version = 1)
abstract class UserRoomDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
}