package com.websarva.wings.android.flat.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hadilq.liveevent.LiveEvent
import com.websarva.wings.android.flat.FLATApplication
import com.websarva.wings.android.flat.api.ResponseData
import com.websarva.wings.android.flat.model.User
import com.websarva.wings.android.flat.repository.ApiRepository
import kotlinx.coroutines.launch

class MapViewModel : ViewModel() {
    private val apiRepository = ApiRepository.instance
    private val roomRepository = FLATApplication.userRoomRepository

    private val _user = LiveEvent<User>()
    // これ自分の情報だけど，ここでほしいのは友人の情報
    val user: LiveData<User> get() = _user

    private val _isUpdated = LiveEvent<Int>()
    val isUpdated: LiveData<Int> get() = _isUpdated

    private val _roomChanged = LiveEvent<Boolean>()
    val roomChanged: LiveData<Boolean> get() = _roomChanged

    fun deleteUserData() {
        viewModelScope.launch {
            roomRepository.deleteAll()
            _roomChanged.postValue(true)
        }
    }

    fun getUserData() {
        viewModelScope.launch {
            _user.postValue(roomRepository.getUserData())
        }
    }
}