package com.websarva.wings.android.flat.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.websarva.wings.android.flat.api.PostData
import com.websarva.wings.android.flat.repository.ApiRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import androidx.lifecycle.MediatorLiveData


class FriendListViewModel(
    saveStateHandle: SavedStateHandle
) : ViewModel() {
    private val repository = ApiRepository.instance

    //TODO::debugはここの値を変更して行う
    private val myId = "900000"

    private val _mutualFriends = MutableLiveData<List<ListItem.MutualItem>?>()
    val mutualFriends: LiveData<List<ListItem.MutualItem>?> get() = _mutualFriends

    private val _oneSideFriends = MutableLiveData<List<ListItem.OneSideItem>?>()
    val oneSideFriends: LiveData<List<ListItem.OneSideItem>?> get() = _oneSideFriends

    // 友達リストの変更を通知 index0はonside, index1はmutualの友だち数を格納する
    val friends: MediatorLiveData<MutableList<Int>> = MediatorLiveData<MutableList<Int>>()

    private val _getFriendsCode = MutableLiveData<Int>()
    val getFriendsCode: LiveData<Int> get() = _getFriendsCode

    private val _postAcceptFriendCode = MutableLiveData<Int>()
    val postAcceptFriendCode: LiveData<Int> get() = _postAcceptFriendCode

    private val _postRejectFriendCode = MutableLiveData<Int>()
    val postRejectFriendCode: LiveData<Int> get() = _postRejectFriendCode

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    init {
        initFriendsCount()
        getFriends()

        friends.addSource(oneSideFriends) {
            var count = 0
            if (!it.isNullOrEmpty()) {
                count = it.size
            }
            Log.d("one", "$count")
            val list = friends.value
            list!![0] = count
            friends.postValue(list)
        }
        friends.addSource(mutualFriends) {
            var count = 0
            if (!it.isNullOrEmpty()) {
                count = it.size
            }
            Log.d("mu", "$count")
            val list = friends.value
            list!![1] = count
            friends.postValue(list)
        }
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
                } else {
                    Log.d("getFriendFailure", "${response}\n${response.body()}")
                }
            } catch (e: Exception) {
                _errorMessage.postValue(e.message)
                e.printStackTrace()
            }
        }
    }

    fun postAcceptFriend(targetId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val postId = PostData.PostFriends(myId, targetId)
                val response = repository.postAddFriend(postId)
                _postAcceptFriendCode.postValue(response.code())
                if (response.isSuccessful) {
                    Log.d(
                        "acceptSuccess",
                        "${response}\n${response.body()}\nmyId=${myId}, targetId=${targetId}"
                    )
                } else {
                    Log.d("acceptFailure", "$response")
                }
            } catch (e: Exception) {
                _errorMessage.postValue(e.message)
                e.printStackTrace()
            }
        }
    }

    fun postRejectFriend(targetId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val postId = PostData.PostFriends(myId, targetId)
                val response = repository.postRejectFriend(postId)
                _postRejectFriendCode.postValue(response.code())
                if (response.isSuccessful) {
                    Log.d(
                        "rejectSuccess",
                        "${response}\n${response.body()}\nmyId=${myId}, targetId=${targetId}"
                    )
                } else {
                    Log.d("rejectFailure", "$response")
                }
            } catch (e: Exception) {
                _errorMessage.postValue(e.message)
                e.printStackTrace()
            }
        }
    }

    private fun initFriendsCount() {
        val counter = MutableList(2) { 0 }
        friends.postValue(counter)
    }
}