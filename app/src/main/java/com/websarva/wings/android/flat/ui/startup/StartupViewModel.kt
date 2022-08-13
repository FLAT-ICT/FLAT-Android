package com.websarva.wings.android.flat.ui.startup

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.websarva.wings.android.flat.FLATApplication
import com.websarva.wings.android.flat.repository.ApiRepository
import kotlinx.coroutines.launch

class StartupViewModel : ViewModel() {
    private val apiRepository = ApiRepository.instance
    private val roomRepository = FLATApplication.userRoomRepository

//    private val _user = LiveEvent<User>()
//    val user : LiveData<User> get() = _user

    //    val user = roomRepository.getUserIdLiveData()
//    val userId = roomRepository.getUserIdLiveData()
//    val user = roomRepository.getUserData()

    fun getUserId(): Int {
        var userId = 0
        viewModelScope.launch {
            userId = if (roomRepository.getUserData() != null) {
                roomRepository.getUserData()!!.myId?:0
            } else {
                0
            }
        }
        Log.d("userId vm", "$userId")
        return userId
    }
}
