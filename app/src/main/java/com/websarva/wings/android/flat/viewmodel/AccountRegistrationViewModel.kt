package com.websarva.wings.android.flat.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.hadilq.liveevent.LiveEvent
import com.websarva.wings.android.flat.FLATApplication
import com.websarva.wings.android.flat.FLATApplication.Companion.myId
import com.websarva.wings.android.flat.api.PostData
import com.websarva.wings.android.flat.api.ResponseData
import com.websarva.wings.android.flat.model.User
import com.websarva.wings.android.flat.repository.ApiRepository
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

    data class UserInputData(
        val name: String,
        val pass1: String,
        val pass2: String,
        var isMatch: Boolean,
        var isCharaLenOk: Boolean
    )

    private val _isMatchPassword = LiveEvent<UserInputData>()
    val isMatchPassword: LiveData<UserInputData> get() = _isMatchPassword

    private val _isCharacterOk = LiveEvent<UserInputData>()
    val isCharacterOk: LiveData<UserInputData> get() = _isCharacterOk

    private val _isLengthOk = LiveEvent<UserInputData>()
    val isLengthOk: LiveData<UserInputData> get() = _isLengthOk

    fun registerUser(inputData: UserInputData) {
        if (inputData.name != "") {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val postData = PostData.RegisterData(inputData.name, inputData.pass1)
                    val response = apiRepository.postRegister(postData)
                    _postCode.postValue(response.code())
                    if (response.isSuccessful) {
                        _userData.postValue(response.body())
                        Log.d(
                            "RegisterSuccess",
                            "${response}\n${response.body()}\nmyId=${response.body()?.id}"
                        )
                    } else {
                        Log.d("RegisterFailure", "$response")
                    }
                } catch (e: Exception) {
                    _errorMessage.postValue(e.message)
                    e.printStackTrace()
                }
            }
        }
    }

    fun checkMatchPassword(inputData: UserInputData) {
        inputData.isMatch = inputData.pass1 == inputData.pass2
        _isMatchPassword.postValue(inputData)
    }

    fun checkCharacter(inputData: UserInputData) {
        val reAlphaNum = Regex("^[A-Za-z0-9]+$")
        inputData.isCharaLenOk = inputData.pass1.matches(reAlphaNum)
        _isCharacterOk.postValue(inputData)
    }

    fun checkPasswordLength(inputData: UserInputData) {
        inputData.isCharaLenOk = inputData.pass1.length in 8..256
        _isLengthOk.postValue(inputData)
    }

    fun registerUserInRoom() {
        val user = User(
            myId = userData.value!!.id,
            name = userData.value!!.name,
            status = userData.value!!.status,
            spot = userData.value?.spot,
            iconPath = userData.value!!.icon_path
        )
        viewModelScope.launch {
            if (isExistData()) {
                updateUserAccount(user)
            } else {
                insertUserData(user)
            }
            myId = roomRepository.getUserData().myId
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