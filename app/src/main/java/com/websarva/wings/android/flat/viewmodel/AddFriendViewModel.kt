package com.websarva.wings.android.flat.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.websarva.wings.android.flat.api.PostData.PostFriends
import com.websarva.wings.android.flat.api.ResponseData.ResponseSearchUsers
import com.websarva.wings.android.flat.api.ResponseData.ResponseCheckFriend
import com.websarva.wings.android.flat.repository.ApiRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class AddFriendViewModel: ViewModel() {
    private val repository = ApiRepository.instance

    //TODO::後で下のmyIDを削除して命名を変更する
    private val id: Int = 1

    private val _users = MutableLiveData<List<ResponseSearchUsers>>()
    val users: LiveData<List<ResponseSearchUsers>> get() = _users

    //TODO::repositoryでroomか何かと繋いで自分のIDを取ってくるようにする？
    private val _myId = MutableLiveData("000001")
    val myId: LiveData<String> get() = _myId

    private val _user = MutableLiveData<ResponseCheckFriend>()
    val user: LiveData<ResponseCheckFriend> get() = _user

    private val _getCode = MutableLiveData<Int>()
    val getCode: LiveData<Int> get() = _getCode

    private val _postCode = MutableLiveData<Int>()
    val postCode: LiveData<Int> get() = _postCode

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage


    fun getSearchUsers(targetName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.searchUsers(id, targetName)
                _getCode.postValue(response.code())
                if (response.isSuccessful) {
                    Log.d("getSearchSuccess", "${response}\n${response.body()}")
                    _users.postValue(response.body())
                } else {
                    Log.d("getSearchFailure", "$response")
                }
            } catch (e: Exception) {
                _errorMessage.postValue(e.message)
                e.printStackTrace()
            }
        }
    }

    fun getCheckFriend(targetId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.checkFriend(myId.value.toString(), targetId)
                _getCode.postValue(response.code())
                if (response.isSuccessful) {
                    Log.d("getSuccess", "${response}\n${response.body()}")
                    _user.postValue(response.body())
                } else {
                    Log.d("getFailure", "$response")
                }
            } catch (e: Exception) {
                _errorMessage.postValue(e.message)
                e.printStackTrace()
            }
        }
    }

    // 後に別のファイルに移す
//    fun getUserInfo(id: String) {
//        viewModelScope.launch(Dispatchers.IO) {
//            try {
//                val response = repository.getUser(id)
//                _getCode.postValue((response.code()))
//                if (response.isSuccessful) {
//                    Log.d("getSuccess", "${response}\n${response.body()}")
//                    _user.postValue(response.body())
//                } else {
//                    Log.d("getFailure", "$response")
//                }
//            } catch (e: Exception) {
//                _errorMessage.postValue(e.message)
//                e.printStackTrace()
//            }
//        }
//    }

    fun postFriendRequest(targetId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val postId = PostFriends(myId.value.toString(), targetId)
                val response = repository.postAddFriend(postId)
                _postCode.postValue(response.code())
                if (response.isSuccessful) {
                    Log.d("postSuccess", "${response}\n${response.body()}\nmyId=${myId.value.toString()}, targetId=${targetId}")
                } else {
                    Log.d("postFailure", "$response")
                }
            } catch (e: Exception) {
                _errorMessage.postValue(e.message)
                e.printStackTrace()
            }
        }
    }
}