package com.websarva.wings.android.flat.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.websarva.wings.android.flat.FLATApplication
import com.websarva.wings.android.flat.api.PostData
import com.websarva.wings.android.flat.api.ResponseData
import com.websarva.wings.android.flat.model.User
import com.websarva.wings.android.flat.model.UserRoomDatabase
import com.websarva.wings.android.flat.model.UserRoomDatabase.Companion.getDatabase
import com.websarva.wings.android.flat.repository.ApiRepository
import com.websarva.wings.android.flat.repository.UserRoomRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class AccountRegistrationViewModel : ViewModel() {
    private val apiRepository = ApiRepository.instance
    private val roomRepository = FLATApplication.userRoomRepository

    private val _postCode = MutableLiveData<Int>()
    val postCode: LiveData<Int> get() = _postCode

    private val _userData = MutableLiveData<ResponseData.ResponseGetUser>()
    val userData: LiveData<ResponseData.ResponseGetUser> get() = _userData

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private fun registerUser(name: String, password: String){
        viewModelScope.launch(Dispatchers.IO){
            try {
                val postData = PostData.RegisterData(name, password)
                val response = apiRepository.postRegister(postData)
                _postCode.postValue(response.code())
                if (response.isSuccessful) {
                    _userData.postValue(response.body())
                    Log.d("RegisterSuccess", "${response}\n${response.body()}\nmyId=${response.body()?.id}")
                } else {
                    Log.d("RegisterFailure", "$response")
                }
            } catch (e: Exception) {
                _errorMessage.postValue(e.message)
                e.printStackTrace()
            }
        }
    }

    fun onRegisterButtonClicked(name: String, password: String) {
        if (name != "" && password != "") {
            registerUser(name, password)
        }
    }

    fun checkPassword(pass1: String, pass2: String){
        //TODO: passwordが一致しているかどうかを判断する処理を書く
    }

    fun registerUserInRoom() {
        val user = User(
            myId = userData.value!!.id,
            name = userData.value!!.name,
            status = userData.value!!.status,
            spot = userData.value!!.spot,
            iconPath = userData.value!!.icon_path
        )
        viewModelScope.launch {
            if (isExistData()) {
                updateUserAccount(user)
            } else {
                insertUserData(user)
            }
        }
    }

    private suspend fun updateUserAccount(user: User) {
        deleteUserData()
        insertUserData(user)
    }

    private suspend fun insertUserData(user: User) {
        roomRepository.insert(user)
    }

    private suspend fun deleteUserData() {
        roomRepository.deleteAll()
    }

    private suspend fun isExistData(): Boolean {
        return when (roomRepository.countData()) {
            0 -> false
            else -> true
        }
    }
}