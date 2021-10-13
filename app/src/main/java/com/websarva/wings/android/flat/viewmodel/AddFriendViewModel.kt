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

    private val postId = MutableLiveData<PostAddFriend>()
    val user = MutableLiveData<ResponseGetUser>()
    val errorMessage = MutableLiveData<String>()

    fun getUserInfo(id: String){
        viewModelScope.launch{
            val response = repository.getUser(id)
            if (response.isSuccessful) {
                    Log.d("getSuccess", "$response")
                    user.postValue(repository.getUser(id).body())
            } else {
                Log.d("getFailure", "$response")
                errorMessage.postValue(response.message())
            }
        }
    }
}