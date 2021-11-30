package com.websarva.wings.android.flat.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.hadilq.liveevent.LiveEvent
import com.websarva.wings.android.flat.FLATApplication
import com.websarva.wings.android.flat.repository.ApiRepository

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

    //TODO: is_loggedinのレスポンスを格納するLiveData

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

    //TODO: is_loggedinのGET

    //TODO: loginのPOST

    //TODO: login結果を受けて、roomにデータを格納する
}