package com.websarva.wings.android.flat.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hadilq.liveevent.LiveEvent
import com.websarva.wings.android.flat.FLATApplication
import com.websarva.wings.android.flat.FLATApplication.Companion.myId
import com.websarva.wings.android.flat.api.PostData
import com.websarva.wings.android.flat.api.ResponseData
import com.websarva.wings.android.flat.model.User
import com.websarva.wings.android.flat.repository.ApiRepository
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception

class StatusChangeViewModel: ViewModel() {
    private val apiRepository = ApiRepository.instance
    private val roomRepository = FLATApplication.userRoomRepository

    private val _errorMessage = LiveEvent<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _postResponse = LiveEvent<Response<ResponseData.ResponseGetUser>>()
    val postResponse: LiveData<Response<ResponseData.ResponseGetUser>> get() = _postResponse

    private val _isUpdated = LiveEvent<Boolean>()
    val isUpdated: LiveData<Boolean> get() = _isUpdated

    fun postUpdateStatus(status: Int) {
        viewModelScope.launch {
            try {
                val postData = PostData.UpdateStatus(myId, status)
                val response =  apiRepository.updateStatus(postData)
                Log.d("postData", "$postData")
                _postResponse.postValue(response)
                if (response.isSuccessful) {
                    Log.d(
                        "UpdateStatusSuccess",
                        "${response}\n${response.body()}"
                    )
                } else {
                    Log.d("UpdateStatusFailure", "$response")
                }
            } catch (e: Exception) {
                _errorMessage.postValue(e.message)
                e.printStackTrace()
            }
        }
    }

    fun updateRoom(userData: ResponseData.ResponseGetUser) {
        val user = User(
            myId = userData.id,
            name = userData.name,
            status = userData.status,
            spot = userData.spot,
            iconPath = userData.icon_path,
            loggedInAt = userData.logged_in_at
        )
        viewModelScope.launch {
            roomRepository.update(user)
            _isUpdated.postValue(true)
        }
    }
}