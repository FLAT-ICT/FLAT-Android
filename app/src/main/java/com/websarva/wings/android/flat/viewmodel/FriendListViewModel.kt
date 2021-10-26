package com.websarva.wings.android.flat.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.websarva.wings.android.flat.api.PostData
import com.websarva.wings.android.flat.repository.ApiRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import androidx.lifecycle.MediatorLiveData
import com.hadilq.liveevent.LiveEvent
import com.websarva.wings.android.flat.api.ResponseData


class FriendListViewModel(
    saveStateHandle: SavedStateHandle
) : ViewModel() {
    private val repository = ApiRepository.instance

    //TODO::debugはここの値を変更して行う
    private val myId = 2003

    val friendsCount: MediatorLiveData<MutableMap<String, Int>> = MediatorLiveData<MutableMap<String, Int>>()

    val operationUnapprovedFriends: LiveEvent<List<Int>> = LiveEvent()

    private val _friends = MutableLiveData<ResponseData.ResponseGetFriends?>()
    val friends: LiveData<ResponseData.ResponseGetFriends?> get() = _friends

    private val _getFriendsCode = MutableLiveData<Int>()
    val getFriendsCode: LiveData<Int> get() = _getFriendsCode

    private val postAcceptFriendCode = MutableLiveData<Int>()

    private val postRejectFriendCode = MutableLiveData<Int>()

    init {
        getFriends()

        friendsCount.addSource(friends) {
            var oneSideCount = 0
            var mutualCount = 0
            if (!it?.one_side.isNullOrEmpty()) {
                oneSideCount = it!!.one_side!!.size
            }
            if (!it?.mutual.isNullOrEmpty()) {
                mutualCount = it!!.mutual!!.size
            }
            val data = mutableMapOf<String, Int>()
            data["oneSideCount"] = oneSideCount
            data["mutualCount"] = mutualCount
            friendsCount.postValue(data)
        }

        operationUnapprovedFriends.addSource(postAcceptFriendCode) {
            val data: List<Int> = listOf(0, postAcceptFriendCode.value!!)
            operationUnapprovedFriends.postValue(data)
        }
        operationUnapprovedFriends.addSource(postRejectFriendCode) {
            val data: List<Int> = listOf(1, postRejectFriendCode.value!!)
            operationUnapprovedFriends.postValue(data)
        }
    }

    fun getFriends() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getFriends(myId)
                _getFriendsCode.postValue(response.code())
                if (response.isSuccessful) {
                    Log.d("getFriendSuccess", "$response")
                    val friendList = response.body()
                    _friends.postValue(friendList)
                } else {
                    Log.d("getFriendFailure", "${response}\n${response.body()}")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun postApproveFriend(targetId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val postId = PostData.PostFriends(myId, targetId)
                val response = repository.postAddFriend(postId)
                postAcceptFriendCode.postValue(response.code())
                if (response.isSuccessful) {
                    Log.d(
                        "approveSuccess",
                        "${response}\n${response.body()}\nmyId=${myId}, targetId=${targetId}"
                    )
                } else {
                    Log.d("approveFailure", "$response")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun postRejectFriend(targetId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val postId = PostData.PostFriends(myId, targetId)
                val response = repository.postRejectFriend(postId)
                postRejectFriendCode.postValue(response.code())
                if (response.isSuccessful) {
                    Log.d(
                        "rejectSuccess",
                        "${response}\n${response.body()}\nmyId=${myId}, targetId=${targetId}"
                    )
                } else {
                    Log.d("rejectFailure", "$response")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}