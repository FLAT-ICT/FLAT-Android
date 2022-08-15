package com.websarva.wings.android.flat.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.websarva.wings.android.flat.model.User
import com.websarva.wings.android.flat.model.UserDao

class UserRoomRepository(private val dao: UserDao) {

    suspend fun getUserData(): User ?{
        return dao.getUserData()
    }

    suspend fun insert(user: User) {
        return dao.insert(user)
    }

    suspend fun update(user: User) {
        return dao.update(user)
    }

    suspend fun delete(user: User) {
        return dao.delete(user)
    }

    suspend fun deleteAll() {
        return dao.deleteAll()
    }

    suspend fun countData(): Int {
        return dao.countData()
    }
}