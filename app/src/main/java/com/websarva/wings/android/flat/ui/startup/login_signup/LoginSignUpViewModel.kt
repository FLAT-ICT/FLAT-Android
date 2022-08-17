package com.websarva.wings.android.flat.ui.startup.login_signup

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hadilq.liveevent.LiveEvent
import com.websarva.wings.android.flat.FLATApplication
import com.websarva.wings.android.flat.R
import com.websarva.wings.android.flat.api.PostData
import com.websarva.wings.android.flat.api.ResponseData
import com.websarva.wings.android.flat.model.User
import com.websarva.wings.android.flat.repository.ApiRepository
import com.websarva.wings.android.flat.ui.startup.inputValidations.FocusedTextFieldKey
import com.websarva.wings.android.flat.ui.startup.inputValidations.InputValidator
import com.websarva.wings.android.flat.ui.startup.inputValidations.InputWrapper
import com.websarva.wings.android.flat.ui.startup.inputValidations.ScreenEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

const val NAME = "name"
const val CREDIT_CARD_NUMBER = "ccNumber"
const val PASSWORD = "password"

class InputErrors(
    val nameErrorId: Int?,
    val cardErrorId: Int?
)

data class LoginInputData(
    var name: String,
    val password: String,
//    var isNameOk: Boolean,
//    var isPasswordOk: Boolean
    val areInputsValid: Boolean
)

@HiltViewModel
class LoginSignUpViewModel @Inject constructor(
    private val handle: SavedStateHandle
//    @Assisted private val handle: SavedStateHandle
) : ViewModel() {
    // Login and SignUp

    private val apiRepository = ApiRepository.instance
    private val roomRepository = FLATApplication.userRoomRepository

    private val _checkResult = LiveEvent<LoginInputData>()
    val checkResult: LiveData<LoginInputData> get() = _checkResult

    private val _preLoginResponse = LiveEvent<Response<ResponseData.ResponsePreLogin>>()
    val preLoginResponse: LiveData<Response<ResponseData.ResponsePreLogin>> get() = _preLoginResponse

    private val _loginResponse = LiveEvent<Response<ResponseData.ResponseGetUser>>()
    val loginResponse: LiveData<Response<ResponseData.ResponseGetUser>> get() = _loginResponse

    private val _roomChanged = LiveEvent<Boolean>()
    val roomChanged: LiveData<Boolean> get() = _roomChanged

    private val _error = LiveEvent<String>()
    val error: LiveData<String> get() = _error

    fun preLogin(inputData: LoginInputData) {
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

    fun login(inputData: LoginInputData) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val postData = PostData.RegisterData(inputData.name, inputData.password)
                val response = apiRepository.postLogin(postData)
                _loginResponse.postValue(response)
                Log.d("LoginSuccess", "$response")
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

    fun upsertUserIntoRoom(userData: ResponseData.ResponseGetUser) {
        val user = User(
            myId = userData.id,
            name = userData.name,
            status = userData.status,
            spot = userData.spot,
            iconPath = userData.icon_path,
            loggedInAt = userData.logged_in_at
        )
        viewModelScope.launch {
            upsertUserData(user)
            _roomChanged.postValue(true)
        }
    }

//    private suspend fun updateUserAccount(user: User) {
//        deleteUserData()
//        insertUserData(user)
//    }

    private suspend fun upsertUserData(user: User) {
        roomRepository.upsert(user)
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

    // Input validation

    val name = handle.getStateFlow(NAME, InputWrapper())
    val password = handle.getStateFlow(PASSWORD, InputWrapper())
    val areInputsValid = combine(name, password) { name, password ->
        name.value.isNotEmpty() && name.errorId == null &&
                password.value.isNotEmpty() && password.errorId == null
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)
    private var focusedTextField = handle["focusedTextField"] ?: FocusedTextFieldKey.NAME
        set(value) {
            field = value
            handle["focusedTextField"] = value
        }

    private val _events = Channel<ScreenEvent>()
    val events = _events.receiveAsFlow()

    init {
        if (focusedTextField != FocusedTextFieldKey.NONE) focusOnLastSelectedTextField()
    }

    fun onNameEntered(input: String) {
        val errorId = InputValidator.getNameErrorIdOrNull(input)
        handle[NAME] = name.value.copy(value = input, errorId = errorId)
    }

    fun onPasswordEntered(input: String) {
        val errorId = InputValidator.getPasswordErrorIdOrNull(input)
        handle[PASSWORD] = password.value.copy(value = input, errorId = errorId)
    }

    fun onTextFieldFocusChanged(key: FocusedTextFieldKey, isFocused: Boolean) {
        focusedTextField = if (isFocused) key else FocusedTextFieldKey.NONE
    }

    fun onNameImeActionClick() {
        viewModelScope.launch(Dispatchers.Default) {
            _events.send(ScreenEvent.MoveFocus())
        }
    }

    fun onPasswordImeActionClick() {
        viewModelScope.launch(Dispatchers.Default) {
            _events.send(ScreenEvent.MoveFocus())
        }
    }

    fun onContinueClick() {
        viewModelScope.launch(Dispatchers.Default) {
            if (areInputsValid.value) clearFocusAndHideKeyboard()
            val resId = if (areInputsValid.value) R.string.success else R.string.validation_error
            _events.send(ScreenEvent.ShowToast(resId))
        }
    }

    private suspend fun clearFocusAndHideKeyboard() {
        _events.send(ScreenEvent.ClearFocus)
        _events.send(ScreenEvent.UpdateKeyboard(false))
        focusedTextField = FocusedTextFieldKey.NONE
    }

    private fun focusOnLastSelectedTextField() {
        viewModelScope.launch(Dispatchers.Default) {
            _events.send(ScreenEvent.RequestFocus(focusedTextField))
            delay(250)
            _events.send(ScreenEvent.UpdateKeyboard(true))
        }
    }
}