package com.websarva.wings.android.flat.model

import androidx.room.*

@Dao
interface UserDao {
    @Insert
    suspend fun insert(user: User)

    @Update
    suspend fun update(user : User)

    @Delete
    suspend fun delete(user : User)

    @Query("SELECT * FROM user_table")
    fun getUserData(): User
}