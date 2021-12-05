package com.websarva.wings.android.flat.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hadilq.liveevent.LiveEvent
import com.websarva.wings.android.flat.FLATApplication
import com.websarva.wings.android.flat.repository.ApiRepository

class NameChangeViewModel: ViewModel() {
    private val apiRepository = ApiRepository.instance
    private val roomRepository = FLATApplication.userRoomRepository

    private val _errorMessage = LiveEvent<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _trimmedName = LiveEvent<String>()
    val trimmedName: LiveData<String> get() = _trimmedName

    fun trimName(name: String) {
        val trimName = name.trim()
        _trimmedName.postValue(trimName)
    }

    //TODO: update name api

    //TODO: update name room
}