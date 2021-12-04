package com.websarva.wings.android.flat.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hadilq.liveevent.LiveEvent
import com.websarva.wings.android.flat.FLATApplication
import com.websarva.wings.android.flat.R
import com.websarva.wings.android.flat.api.PostData
import com.websarva.wings.android.flat.api.ResponseData
import com.websarva.wings.android.flat.model.UserSettingItem
import com.websarva.wings.android.flat.repository.ApiRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class UserSettingViewModel : ViewModel() {
    private val apiRepository = ApiRepository.instance
    private val roomRepository = FLATApplication.userRoomRepository

    private val _logoutResponse = LiveEvent<Response<ResponseData.ResponsePost>>()
    val logoutResponse: LiveData<Response<ResponseData.ResponsePost>> get() = _logoutResponse

    private val _roomChanged = LiveEvent<Boolean>()
    val roomChanged: LiveData<Boolean> get() = _roomChanged

    private val _error = LiveEvent<String>()
    val error: LiveData<String> get() = _error

    fun logout(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val postData = PostData.PostLogout(id)
                val response = apiRepository.postLogout(postData)
                _logoutResponse.postValue(response)
                if (response.isSuccessful) {
                    Log.d(
                        "LogoutSuccess",
                        "${response}\n${response.body()}"
                    )
                } else {
                    Log.d("LogoutFailure", "$response")
                }
            } catch (e: Exception) {
                _error.postValue(e.message)
                e.printStackTrace()
            }
        }
    }

    fun deleteUserData() {
        viewModelScope.launch {
            roomRepository.deleteAll()
            _roomChanged.postValue(true)
        }
    }

    fun setColor(item: UserSettingItem): Int {
        return when (item.id) {
            3 -> {
                R.color.red
            }
            else -> {
                R.color.dark
            }
        }
    }
}