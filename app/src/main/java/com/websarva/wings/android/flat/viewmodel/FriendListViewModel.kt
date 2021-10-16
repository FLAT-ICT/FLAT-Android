package com.websarva.wings.android.flat.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.websarva.wings.android.flat.api.ResponseData.ResponseGetFriends
import com.websarva.wings.android.flat.repository.ApiRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class FriendListViewModel(
    saveStateHandle: SavedStateHandle
): ViewModel() {
    private val repository = ApiRepository.instance

    //TODO::debugはここの値を変更して行う
    private val myId = "000001"

    private val _friendList = MutableLiveData<ResponseGetFriends>()
    val friendList: LiveData<ResponseGetFriends> get() = _friendList

    private val _getFriendsCode = MutableLiveData<Int>()
    val getFriendsCode: LiveData<Int> get() = _getFriendsCode

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun getFriends() {
        viewModelScope.launch(Dispatchers.IO){
            try {
                val response = repository.getFriends(myId)
                _getFriendsCode.postValue(response.code())
                if (response.isSuccessful) {
                    Log.d("getFriendSuccess", "${response}\n${response.body()}")
                    _friendList.postValue(response.body())
                } else {
                    Log.d("getFriendSuccess", "${response}\n${response.body()}")
                }
            } catch (e: Exception) {
                _errorMessage.postValue(e.message)
                e.printStackTrace()
            }
        }
    }
}