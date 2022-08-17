package com.websarva.wings.android.flat.ui.startup.login_signup

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hadilq.liveevent.LiveEvent
import com.websarva.wings.android.flat.FLATApplication
import com.websarva.wings.android.flat.api.PostData
import com.websarva.wings.android.flat.api.ResponseData
import com.websarva.wings.android.flat.model.User
import com.websarva.wings.android.flat.repository.ApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val handle: SavedStateHandle
) : ViewModel() {
    private val apiRepository = ApiRepository.instance
    private val roomRepository = FLATApplication.userRoomRepository

    data class LoginInput(
        var name: String,
        val password: String,
        var isNameOk: Boolean,
        var isPasswordOk: Boolean
    )

    private val _checkResult = LiveEvent<LoginInput>()
    val checkResult: LiveData<LoginInput> get() = _checkResult

    private val _preLoginResponse = LiveEvent<Response<ResponseData.ResponsePreLogin>>()
    val preLoginResponse: LiveData<Response<ResponseData.ResponsePreLogin>> get() = _preLoginResponse

    private val _loginResponse = LiveEvent<Response<ResponseData.ResponseGetUser>>()
    val loginResponse: LiveData<Response<ResponseData.ResponseGetUser>> get() = _loginResponse

    private val _roomChanged = LiveEvent<Boolean>()
    val roomChanged: LiveData<Boolean> get() = _roomChanged

    private val _error = LiveEvent<String>()
    val error: LiveData<String> get() = _error


    fun checkAndTrim(inputData: LoginInput) {
        val trimName = inputData.name.trim()
        if (trimName == "") {
            inputData.isNameOk = false
        } else {
            inputData.name = trimName
            inputData.isNameOk = true
        }
        inputData.isPasswordOk = inputData.password != ""
        _checkResult.postValue(inputData)
    }

    fun preLogin(inputData: LoginInput) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val postData = PostData.PostPreLogin(inputData.name, inputData.password, null)
                val response = apiRepository.postPreLogin(postData)
                _preLoginResponse.postValue(response)
                if (response.isSuccessful) {
                    Log.d(
                        "PreLoginSuccess",
                        "${response}\n${response.body()}"
                    )
                } else {
                    Log.d("PreLoginFailure", "$response")
                }
            } catch (e: Exception) {
                _error.postValue(e.message)
                e.printStackTrace()
            }
        }
    }

    fun login(inputData: LoginInput) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val postData = PostData.RegisterData(inputData.name, inputData.password)
                val response = apiRepository.postLogin(postData)
                _loginResponse.postValue(response)
                if (response.isSuccessful) {
                    Log.d(
                        "LoginSuccess",
                        "${response}\n${response.body()}"
                    )
                } else {
                    Log.d("LoginFailure", "$response")
                }
            } catch (e: Exception) {
                _error.postValue(e.message)
                e.printStackTrace()
            }
        }
    }

    fun insertUserIntoRoom(userData: ResponseData.ResponseGetUser) {
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
//            FLATApplication.myId = roomRepository.getUserData().myId
            _roomChanged.postValue(true)
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