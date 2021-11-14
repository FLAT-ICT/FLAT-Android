package com.websarva.wings.android.flat.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.websarva.wings.android.flat.api.PostData
import com.websarva.wings.android.flat.api.ResponseData
import com.websarva.wings.android.flat.repository.ApiRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class AccountRegistrationViewModel : ViewModel() {
    private val repository = ApiRepository.instance

    private val _postCode = MutableLiveData<Int>()
    val postCode: LiveData<Int> get() = _postCode

    private val _myId = MutableLiveData<ResponseData.RegisteredData>()
    val myId: LiveData<ResponseData.RegisteredData> get() = _myId

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage
    //TODO: DataClass定義を先にやる

    private fun registerUser(name: String, password: String){
        viewModelScope.launch(Dispatchers.IO){
            try {
                val postData = PostData.RegisterData(name, password)
                val response = repository.postRegister(postData)
                _postCode.postValue(response.code())
                if (response.isSuccessful) {
                    _myId.postValue(response.body())
                    Log.d("RegisterSuccess", "${response}\n${response.body()}\nmyId=${_myId}")
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

    //TODO: myIDをroomに登録(repositoryの関数を呼ぶ?)
}