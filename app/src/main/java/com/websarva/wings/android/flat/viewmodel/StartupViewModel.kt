package com.websarva.wings.android.flat.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.hadilq.liveevent.LiveEvent

class StartupViewModel(
    saveStateHandle: SavedStateHandle
) : ViewModel() {
    private val _loginButtonOnClicked: LiveEvent<Boolean> = LiveEvent()
    val loginButtonOnClicked: LiveData<Boolean> get() = _loginButtonOnClicked
    private val _accountRegistrationButtonOnClocked: LiveEvent<Boolean> = LiveEvent()
    val accountRegistrationButtonOnClicked: LiveData<Boolean> get() = _accountRegistrationButtonOnClocked

    fun loginButtonOnClickListener() {
        _loginButtonOnClicked.postValue(true)
    }

    fun accountRegistrationButtonOnClickListener() {
        _accountRegistrationButtonOnClocked.postValue(true)
    }
}
