package com.websarva.wings.android.flat.ui.startup

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hadilq.liveevent.LiveEvent
import com.websarva.wings.android.flat.FLATApplication
import com.websarva.wings.android.flat.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class StartupViewModel(
    saveStateHandle: SavedStateHandle
) : ViewModel() {
    private val _isReady = MutableStateFlow(false)
    val isReady: StateFlow<Boolean> get() = _isReady
    private val roomRepository = FLATApplication.userRoomRepository

//    var user: User? = null
    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> get() = _user
    init {
        viewModelScope.launch {
            // Coroutine that will be canceled when the ViewModel is cleared.
            _user.value = roomRepository.getUserData()
            _isReady.value = true
            Log.d("UserId in ViewModel: ", "${_user.value?.myId}")
        }
    }
}
