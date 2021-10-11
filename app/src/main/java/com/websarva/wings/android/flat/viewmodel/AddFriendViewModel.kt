package com.websarva.wings.android.flat.viewmodel

import androidx.lifecycle.*
import com.websarva.wings.android.flat.api.PostData.PostAddFriend
import com.websarva.wings.android.flat.api.ResponseData.ResponseGetUser
import com.websarva.wings.android.flat.repository.ApiRepository
import kotlinx.coroutines.launch

class AddFriendViewModel(
    saveStateHandle: SavedStateHandle
) : ViewModel() {
    private val repository = ApiRepository.instance

    private val postId = MutableLiveData<PostAddFriend>()
    val user = MutableLiveData<ResponseGetUser>()

    fun getUserInfo(id: String){
        viewModelScope.launch{
            user.postValue(repository.getUser(id))
        }
    }

}