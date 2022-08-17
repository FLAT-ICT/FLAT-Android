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
    var isReady = false
    private val roomRepository = FLATApplication.userRoomRepository

    var user: User? = null
    init {
        viewModelScope.launch {
            // Coroutine that will be canceled when the ViewModel is cleared.
            user = roomRepository.getUserData()
            isReady = true
            Log.d("UserId in ViewModel: ", "${user?.myId}")
        }
    }
}
