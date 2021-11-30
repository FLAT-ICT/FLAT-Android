package com.websarva.wings.android.flat.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hadilq.liveevent.LiveEvent
import com.websarva.wings.android.flat.FLATApplication
import com.websarva.wings.android.flat.api.PostData
import com.websarva.wings.android.flat.api.ResponseData
import com.websarva.wings.android.flat.repository.ApiRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception

class LoginViewModel : ViewModel() {
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

    //TODO: loginのレスポンスを格納するLiveData

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
                        "${response}\n${response.body()}\n"
                    )
                } else {
                    Log.d("PreLoginFailure", "$response")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

//TODO: loginのPOST

//TODO: login結果を受けて、roomにデータを格納する
}