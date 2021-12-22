package com.websarva.wings.android.flat.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

class NameChangeViewModel: ViewModel() {
    private val apiRepository = ApiRepository.instance
    private val roomRepository = FLATApplication.userRoomRepository

    private val _errorMessage = LiveEvent<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _trimmedName = LiveEvent<String>()
    val trimmedName: LiveData<String> get() = _trimmedName

    private val _postResponse = LiveEvent<Response<ResponseData.ResponseGetUser>>()
    val postResponse: LiveData<Response<ResponseData.ResponseGetUser>> get() = _postResponse

    private val _isUpdated = LiveEvent<Boolean>()
    val isUpdated: LiveData<Boolean> get() = _isUpdated

    fun trimName(name: String) {
        val trimName = name.trim()
        _trimmedName.postValue(trimName)
    }

    fun postUpdateName(newName: String) {
        viewModelScope.launch {
            try {
                val postData = PostData.UpdateName(myId, newName)
                val response = apiRepository.updateName(postData)
                Log.d("postData", "$postData")
                _postResponse.postValue(response)
                if (response.isSuccessful) {
                    Log.d(
                        "UpdateNameSuccess",
                        "${response}\n${response.body()}"
                    )
                } else {
                    Log.d("UpdateNameFailure", "$response")
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