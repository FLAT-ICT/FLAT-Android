package com.websarva.wings.android.flat.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.websarva.wings.android.flat.api.ResponseData.ResponseGetFriends
import com.websarva.wings.android.flat.repository.ApiRepository
import com.websarva.wings.android.flat.view.FriendListAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class FriendListViewModel(
    saveStateHandle: SavedStateHandle
) : ViewModel() {
    private val repository = ApiRepository.instance

    //TODO::debugはここの値を変更して行う
    private val myId = "000302"

    private val _mutualFriends = MutableLiveData<List<ListItem.MutualItem>?>()
    val mutualFriends: LiveData<List<ListItem.MutualItem>?> get() = _mutualFriends

    private val _oneSideFriends = MutableLiveData<List<ListItem.OneSideItem>?>()
    val oneSideFriends: LiveData<List<ListItem.OneSideItem>?> get() = _oneSideFriends

    private val _getFriendsCode = MutableLiveData<Int>()
    val getFriendsCode: LiveData<Int> get() = _getFriendsCode

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    init {
        getFriends()
    }

    private fun getFriends() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getFriends(myId)
                _getFriendsCode.postValue(response.code())
                if (response.isSuccessful) {
                    Log.d("getFriendSuccess", "${response}\n${response.body()}")
                    val friendList = response.body()
                    _oneSideFriends.postValue(friendList?.one_side)
                    _mutualFriends.postValue(friendList?.mutual)
                    Log.d("debug", "\n${mutualFriends.value}")
                } else {
                    Log.d("getFriendFailure", "${response}\n${response.body()}")
                }
            } catch (e: Exception) {
                _errorMessage.postValue(e.message)
                e.printStackTrace()
            }
        }
    }

    //TODO::ここでリストの中身を生成する
    private fun createFriendList(){
    }
}