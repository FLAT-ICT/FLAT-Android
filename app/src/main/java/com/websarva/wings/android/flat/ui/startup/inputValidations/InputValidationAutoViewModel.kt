package com.websarva.wings.android.flat.ui.startup.inputValidations

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.websarva.wings.android.flat.R

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InputValidationAutoViewModel @Inject constructor(
    private val handle: SavedStateHandle
) : ViewModel() {

    val name = handle.getStateFlow(NAME, InputWrapper())
    val creditCardNumber = handle.getStateFlow(CREDIT_CARD_NUMBER, InputWrapper())
    val areInputsValid = combine(name, creditCardNumber) { name, cardNumber ->
        name.value.isNotEmpty() && name.errorId == null &&
                cardNumber.value.isNotEmpty() && cardNumber.errorId == null
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

    fun onCardNumberEntered(input: String) {
        val errorId = InputValidator.getCardNumberErrorIdOrNull(input)
        handle[CREDIT_CARD_NUMBER] = creditCardNumber.value.copy(value = input, errorId = errorId)
    }

    fun onTextFieldFocusChanged(key: FocusedTextFieldKey, isFocused: Boolean) {
        focusedTextField = if (isFocused) key else FocusedTextFieldKey.NONE
    }

    fun onNameImeActionClick() {
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