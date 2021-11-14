package com.websarva.wings.android.flat.model

import androidx.room.*

@Dao
interface UserDao {
    @Insert
    fun insert(user: User)

    @Update
    fun update(user : User)

    @Delete
    fun delete(user : User)

    @Query("SELECT * FROM user_table")
    fun getUserData(): User
}