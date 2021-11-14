package com.websarva.wings.android.flat.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(User::class), version = 1)
abstract class UserRoomDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        // Singletonによってインスタンスが複数同時に開かれるのを防ぐ
        @Volatile
        private var INSTANCE: UserRoomDatabase? = null

        fun getDatabase(context: Context): UserRoomDatabase {
            // インスタンスがあればそれを返し、なければ作って返す
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserRoomDatabase::class.java,
                    "user_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}