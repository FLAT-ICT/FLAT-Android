package com.websarva.wings.android.flat.repository

import android.app.Application
import com.websarva.wings.android.flat.model.User
import com.websarva.wings.android.flat.model.UserDao

class UserRoomRepository(private val dao: UserDao) {

    val user = dao.getUserData()

    suspend fun insert(user: User) {
        return dao.insert(user)
    }

    suspend fun update(user: User) {
        return dao.update(user)
    }

    suspend fun delete(user: User) {
        return dao.delete(user)
    }
}