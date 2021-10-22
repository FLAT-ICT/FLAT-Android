package com.websarva.wings.android.flat.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.websarva.wings.android.flat.api.PostData
import com.websarva.wings.android.flat.repository.ApiRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import androidx.lifecycle.MediatorLiveData
import com.websarva.wings.android.flat.api.ResponseData


class FriendListViewModel(
    saveStateHandle: SavedStateHandle
) : ViewModel() {
    private val repository = ApiRepository.instance

    //TODO::debugはここの値を変更して行う
    private val myId = 2003

    val friendsCount: MediatorLiveData<MutableMap<String, Int>> = MediatorLiveData<MutableMap<String, Int>>()

    val operationUnapprovedFriends: MediatorLiveData<List<Int>> = MediatorLiveData<List<Int>>()

    private val _friends = MutableLiveData<ResponseData.ResponseGetFriends?>()
    val friends: LiveData<ResponseData.ResponseGetFriends?> get() = _friends

    private val _getFriendsCode = MutableLiveData<Int>()
    val getFriendsCode: LiveData<Int> get() = _getFriendsCode

    private val postAcceptFriendCode = MutableLiveData<Int>()

    private val postRejectFriendCode = MutableLiveData<MutableList<Int>>()

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
            Log.d("fri", "oneCount=${oneSideCount}, muCount=$mutualCount")
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
            //TODO::if operate only calling getFriends, delete third argument
            //TODO::postRejectFriends second argument, Dialog's position, and Adapter's adPosition of dialog call also need to delete
            val data: List<Int> = listOf(1, postRejectFriendCode.value!![0], postRejectFriendCode.value!![1])
            operationUnapprovedFriends.postValue(data)
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
                    _friends.postValue(friendList)
                } else {
                    Log.d("getFriendFailure", "${response}\n${response.body()}")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun postAcceptFriend(targetId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val postId = PostData.PostFriends(myId, targetId)
                val response = repository.postAddFriend(postId)
                postAcceptFriendCode.postValue(response.code())
                if (response.isSuccessful) {
                    Log.d(
                        "acceptSuccess",
                        "${response}\n${response.body()}\nmyId=${myId}, targetId=${targetId}"
                    )
                } else {
                    Log.d("acceptFailure", "$response")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun postRejectFriend(targetId: Int, position: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val postId = PostData.PostFriends(myId, targetId)
                val response = repository.postRejectFriend(postId)
                val list = mutableListOf(response.code(), position)
                postRejectFriendCode.postValue(list)
                Log.d("debug", "${postRejectFriendCode.value}")
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