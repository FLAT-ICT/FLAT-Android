package com.websarva.wings.android.flat.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.*
import com.websarva.wings.android.flat.R
import com.websarva.wings.android.flat.api.PostData.PostFriends
import com.websarva.wings.android.flat.api.ResponseData.ResponseSearchUser
import com.websarva.wings.android.flat.repository.ApiRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class AddFriendViewModel: ViewModel() {
    private val repository = ApiRepository.instance

    //TODO::repositoryでroomか何かと繋いで自分のIDを取ってくるようにする？
    private val myId: Int = 1

    private val _users = MutableLiveData<List<ResponseSearchUser>>()
    val users: LiveData<List<ResponseSearchUser>> get() = _users

    private val _getCode = MutableLiveData<Int>()
    val getCode: LiveData<Int> get() = _getCode

    private val _postCode = MutableLiveData<Int>()
    val postCode: LiveData<Int> get() = _postCode

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage


    fun getSearchUsers(targetName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.searchUsers(myId, targetName)
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

    private fun postFriendRequest(targetId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val postId = PostFriends(myId, targetId)
                val response = repository.postAddFriend(postId)
                _postCode.postValue(response.code())
                if (response.isSuccessful) {
                    Log.d("postSuccess", "${response}\n${response.body()}\nmyId=${myId}, targetId=${targetId}")
                } else {
                    Log.d("postFailure", "$response")
                }
            } catch (e: Exception) {
                _errorMessage.postValue(e.message)
                e.printStackTrace()
            }
        }
    }

    fun onClickButton(item: ResponseSearchUser) {
        when {
            item.applied -> {
                //TODO::ダイアログ表示
            }
            else -> {
                postFriendRequest(item.id)
            }
        }
    }

    //ボタンの色、テキスト
    fun setButtonText(item: ResponseSearchUser): Int {
        return when {
            item.applied -> {
                R.string.wait_for_approval
            }
            else -> {
                R.string.apply_for_friend
            }
        }
    }

    fun setButtonBackgroundColor(item: ResponseSearchUser): Int {
        return when {
            item.applied -> {
                R.color.primary_pale
            }
            else -> {
                R.color.primary_solid
            }
        }
    }

    fun setButtonTextColor(item: ResponseSearchUser): Int {
        return when {
            item.applied -> {
                R.color.middle
            }
            else -> {
                R.color.white
            }
        }
    }
}