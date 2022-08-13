package com.websarva.wings.android.flat.ui.startup.accountRegistration

import android.util.Log
import androidx.lifecycle.*
import com.hadilq.liveevent.LiveEvent
import com.websarva.wings.android.flat.FLATApplication
import com.websarva.wings.android.flat.api.PostData
import com.websarva.wings.android.flat.api.ResponseData
import com.websarva.wings.android.flat.model.User
import com.websarva.wings.android.flat.repository.ApiRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception

class AccountRegistrationViewModel : ViewModel() {
    private val apiRepository = ApiRepository.instance
    private val roomRepository = FLATApplication.userRoomRepository

    private val _postResponse = LiveEvent<Response<ResponseData.ResponseGetUser>>()
    val postResponse: LiveData<Response<ResponseData.ResponseGetUser>> get() = _postResponse

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    data class UserInputData(
        var name: String,
        val pass1: String,
        val pass2: String,
        var isNameOk: Boolean,
        var isMatch: Boolean,
        var isCharaLenOk: Boolean
    )

    private val _trimmedName = LiveEvent<UserInputData>()
    val trimmedName: LiveData<UserInputData> get() = _trimmedName

    private val _isMatchPassword = LiveEvent<UserInputData>()
    val isMatchPassword: LiveData<UserInputData> get() = _isMatchPassword

    private val _isCharacterOk = LiveEvent<UserInputData>()
    val isCharacterOk: LiveData<UserInputData> get() = _isCharacterOk

    private val _isLengthOk = LiveEvent<UserInputData>()
    val isLengthOk: LiveData<UserInputData> get() = _isLengthOk

    private val _registerOk = LiveEvent<Boolean>()
    val registerOk: LiveData<Boolean> get() = _registerOk

    fun checkMatchPassword(inputData: UserInputData) {
        inputData.isMatch = inputData.pass1 == inputData.pass2
        _isMatchPassword.postValue(inputData)
    }

    fun checkAndTrimName(inputData: UserInputData) {
        val trimName = inputData.name.trim()
        if (trimName == "") {
            inputData.isNameOk = false
        } else {
            inputData.name = trimName
            inputData.isNameOk = true
        }
        _trimmedName.postValue(inputData)
    }

    fun checkCharacter(inputData: UserInputData) {
        val reAlphaNum = Regex("^[A-Za-z0-9]+$")
        inputData.isCharaLenOk = inputData.pass1.matches(reAlphaNum)
        _isCharacterOk.postValue(inputData)
    }

    fun checkPasswordLength(inputData: UserInputData) {
        inputData.isCharaLenOk = inputData.pass1.length in 8..255
        _isLengthOk.postValue(inputData)
    }

    fun registerUser(inputData: UserInputData) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val postData = PostData.RegisterData(inputData.name, inputData.pass1)
                val response = apiRepository.postRegister(postData)
                _postResponse.postValue(response)
                if (response.isSuccessful) {
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

    fun registerUserInRoom(userData: ResponseData.ResponseGetUser) {
        val user = User(
            myId = userData.id,
            name = userData.name,
            status = userData.status,
            spot = userData.spot,
            iconPath = userData.icon_path,
            loggedInAt = userData.logged_in_at
        )
        viewModelScope.launch {
            if (isExistData()) {
                updateUserAccount(user)
            } else {
                insertUserData(user)
            }
            //TODO: アプリケーションクラスに共有変数として持たせるので本当に良いのか吟味する
//            myId = roomRepository.getUserData()!!.myId
            _registerOk.postValue(true)
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