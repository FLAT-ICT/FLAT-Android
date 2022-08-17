package com.websarva.wings.android.flat.ui.startup

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hadilq.liveevent.LiveEvent
import com.websarva.wings.android.flat.FLATApplication
import com.websarva.wings.android.flat.model.User
import kotlinx.coroutines.launch

class StartupViewModel(
    saveStateHandle: SavedStateHandle
) : ViewModel() {
    private val roomRepository = FLATApplication.userRoomRepository


    private var _user = LiveEvent<User?>()
    val user: LiveEvent<User?> get() = _user

    init {
        viewModelScope.launch {
            _user.value = getUserData()
        }
    }


//    suspend fun getUserData(): LiveData<User?> {
//        return roomRepository.getUserLiveData()
//    }

    suspend fun getUserData(): User? {
        return roomRepository.getUserData()
    }
}
