package com.websarva.wings.android.flat.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.hadilq.liveevent.LiveEvent
import com.websarva.wings.android.flat.FLATApplication
import com.websarva.wings.android.flat.FLATApplication.Companion.myId
import com.websarva.wings.android.flat.api.PostData
import com.websarva.wings.android.flat.api.ResponseData
import com.websarva.wings.android.flat.repository.ApiRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class FriendListViewModel(
    saveStateHandle: SavedStateHandle
) : ViewModel() {
    private val repository = ApiRepository.instance
    private val roomRepository = FLATApplication.userRoomRepository

    val operationUnapprovedFriends: LiveEvent<List<Int>> = LiveEvent()

    val friendsCount: MediatorLiveData<MutableMap<String, Int>> =
        MediatorLiveData<MutableMap<String, Int>>()

    private val _friends = MutableLiveData<ResponseData.ResponseGetFriends>()
    val friends: LiveData<ResponseData.ResponseGetFriends> get() = _friends

    private val _getFriendsCode = MutableLiveData<Int>()
    val getFriendsCode: LiveData<Int> get() = _getFriendsCode

    private val _postApproveFriendCode = MutableLiveData<Int>()

    private val _postRejectFriendCode = MutableLiveData<Int>()

    init {
        friendsCount.addSource(friends) {
            var oneSideCount = 0
            var mutualCount = 0
            if (!it?.one_side.isNullOrEmpty()) {
                oneSideCount = it!!.one_side.size
            }
            if (!it?.mutual.isNullOrEmpty()) {
                mutualCount = it!!.mutual.size
            }
            val data = mutableMapOf<String, Int>()
            data["oneSideCount"] = oneSideCount
            data["mutualCount"] = mutualCount
            friendsCount.postValue(data)
        }

        operationUnapprovedFriends.addSource(_postApproveFriendCode) {
            val data: List<Int> = listOf(0, _postApproveFriendCode.value!!)
            operationUnapprovedFriends.postValue(data)
        }
        operationUnapprovedFriends.addSource(_postRejectFriendCode) {
            val data: List<Int> = listOf(1, _postRejectFriendCode.value!!)
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
                    _friends.postValue(response.body())
                } else {
                    Log.d("getFriendFailure", "${response}\n${response.body()}")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun postApproveFriend(targetId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val postId = PostData.PostFriends(myId, targetId)
                val response = repository.postAddFriend(postId)
                _postApproveFriendCode.postValue(response.code())
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
                e.printStackTrace()
            }
        }
    }

    fun onClickApproveButton(item: ResponseData.OneSide) {
        postApproveFriend(item.id)
    }
}