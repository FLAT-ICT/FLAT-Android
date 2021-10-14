package com.websarva.wings.android.flat.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.websarva.wings.android.flat.api.PostData.PostAddFriend
import com.websarva.wings.android.flat.api.ResponseData.ResponseGetUser
import com.websarva.wings.android.flat.repository.ApiRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class AddFriendViewModel(
    saveStateHandle: SavedStateHandle
) : ViewModel() {
    private val repository = ApiRepository.instance

    private val _postId = MutableLiveData<PostAddFriend>()
    val postId: LiveData<PostAddFriend> get() = _postId

    private val _user = MutableLiveData<ResponseGetUser>()
    val user: LiveData<ResponseGetUser> get() = _user

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun getUserInfo(id: String) {
        viewModelScope.launch {
            val response = repository.getUser(id)
            if (response.isSuccessful) {
                Log.d("getSuccess", "${response.body()}")
                _user.postValue(repository.getUser(id).body())
            } else {
                Log.d("getFailure", "$response")
                _errorMessage.postValue(response.message())
            }
        }
    }

//    fun postFriendRequest(postAddFriend: PostAddFriend) {
//        viewModelScope.launch {
//            val response = repository.postAddFriend(postAddFriend)
//            if (response.isSuccessful) {
//                Log.d("postSuccess", "$response")
//
//            } else {
//                Log.d("postFailure", "$response")
//                errorMessage.postValue(response.message())
//            }
//        }
//    }
}