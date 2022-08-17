package com.websarva.wings.android.flat.model

import androidx.room.*

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)

    @Update
    suspend fun update(user: User)

    @Delete
    suspend fun delete(user: User)

    @Query("DELETE FROM user_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM user_table LIMIT 1")
    suspend fun getUserData(): User?

    @Query("SELECT COUNT(*) FROM user_table")
    suspend fun countData(): Int
}