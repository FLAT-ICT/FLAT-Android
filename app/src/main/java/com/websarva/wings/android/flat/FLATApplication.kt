package com.websarva.wings.android.flat

import android.app.Application
import com.websarva.wings.android.flat.model.UserRoomDatabase
import com.websarva.wings.android.flat.repository.UserRoomRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class FLATApplication: Application() {
    companion object {
        lateinit var userRoomRepository: UserRoomRepository
        val applicationScope = CoroutineScope(SupervisorJob())
    }

    override fun onCreate() {
        super.onCreate()

        val database = UserRoomDatabase.getDatabase(this)
        userRoomRepository = UserRoomRepository(database.userDao())
    }
}