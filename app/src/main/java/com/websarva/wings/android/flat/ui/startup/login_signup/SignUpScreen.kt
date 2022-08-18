package com.websarva.wings.android.flat.ui.startup.login_signup

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import com.websarva.wings.android.flat.R
import com.websarva.wings.android.flat.ui.startup.components.ConfirmButton
import com.websarva.wings.android.flat.ui.startup.components.NameTextField
import com.websarva.wings.android.flat.ui.startup.components.PasswordTextField
import com.websarva.wings.android.flat.ui.startup.inputValidations.FocusedTextFieldKey
import com.websarva.wings.android.flat.ui.startup.inputValidations.ScreenEvent
import com.websarva.wings.android.flat.ui.startup.inputValidations.toast
import com.websarva.wings.android.flat.ui.theme.FLATTheme

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SignUpScreen(onNavigate: (Int) -> Unit) {

    val viewModel: LoginSignUpViewModel = hiltViewModel()

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val events = remember(viewModel.events, lifecycleOwner) {
        viewModel.events.flowWithLifecycle(
            lifecycleOwner.lifecycle,
            Lifecycle.State.STARTED
        )
    }

    val name by viewModel.name.collectAsState()
    val password by viewModel.password.collectAsState()
    val passwordConfirm by viewModel.passwordConfirm.collectAsState()
    val areInputsValid by viewModel.areSignUpInputsValid.collectAsState()

    val nameFocusRequester = remember { FocusRequester() }
    val passwordFocusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        events.collect { event ->
            when (event) {
                is ScreenEvent.ShowToast -> context.toast(event.messageId)
                is ScreenEvent.UpdateKeyboard -> {
                    if (event.show) keyboardController?.show() else keyboardController?.hide()
                }
                is ScreenEvent.ClearFocus -> focusManager.clearFocus()
                is ScreenEvent.RequestFocus -> {
                    when (event.textFieldKey) {
                        FocusedTextFieldKey.NAME -> nameFocusRequester.requestFocus()
                        FocusedTextFieldKey.PASSWORD -> passwordFocusRequester.requestFocus()
                        FocusedTextFieldKey.PASSWORD_CONFIRM -> passwordFocusRequester.requestFocus()
                        else -> {}
                    }
                }
                is ScreenEvent.MoveFocus -> focusManager.moveFocus(event.direction)
            }
        }
    }

    FLATTheme {
        Surface() {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.padding(top = 25.dp))
                Text(
                    text = "アカウント登録",
                    Modifier
                        .fillMaxWidth()
                        .padding(start = 24.dp)
                )
                // CustomTextField: ユーザー名
                Spacer(modifier = Modifier.padding(top = 48.dp))
                NameTextField(
                    focusRequester = nameFocusRequester,
                    onFocusChanged = { focusState ->
                        viewModel.onTextFieldFocusChanged(
                            key = FocusedTextFieldKey.NAME,
                            isFocused = focusState.isFocused
                        )
                    },
                    inputWrapper = name,
                    onValueChange = viewModel::onNameEntered,
                    onImeAction = viewModel::onNameImeActionClick
                )
                Spacer(Modifier.height(16.dp))

                PasswordTextField(
                    focusRequester = passwordFocusRequester,
                    onFocusChanged = { focusState ->
                        viewModel.onTextFieldFocusChanged(
                            key = FocusedTextFieldKey.PASSWORD,
                            isFocused = focusState.isFocused
                        )
                    },
                    inputWrapper = password,
                    onValueChange = viewModel::onPasswordEntered,
                    onImeAction = viewModel::onPasswordImeActionClick
                )
                Spacer(Modifier.height(16.dp))

                PasswordTextField(
                    focusRequester = passwordFocusRequester,
                    onFocusChanged = { focusState ->
                        viewModel.onTextFieldFocusChanged(
                            key = FocusedTextFieldKey.PASSWORD_CONFIRM,
                            isFocused = focusState.isFocused
                        )
                    },
                    inputWrapper = passwordConfirm,
                    onValueChange = viewModel::onPasswordConfirmEntered,
                    onImeAction = viewModel::onPasswordImeActionClick
                )

                Spacer(Modifier.height(32.dp))
                ConfirmButton(
                    onCLick = {
                        viewModel.signUp(
                            inputData = SignupInputData(
                                name.value,
                                password.value,
                                passwordConfirm.value,
                                areInputsValid
                            )
                        )
                        viewModel.loginResponse.observe(lifecycleOwner) { response ->
                            when (response.code()) {
                                200 -> {
                                    viewModel.roomChanged.observe(lifecycleOwner) { room ->
                                        when (room) {
                                            true -> onNavigate(R.id.friendListFragment)
                                            false -> context.toast(R.string.connection_error)
                                        }
                                    }
                                }
                                else -> {
                                    context.toast(R.string.connection_error)
                                }
                            }
                        }
                    },
                    enabled = areInputsValid,
                    labelResId = R.string.register
                )
                Spacer(modifier = Modifier.weight(1f))
                // Text: アカウントを持っている人はログインする
                ToLoginText(onNavigate = onNavigate)
                Spacer(modifier = Modifier.padding(top = 32.dp))
            }
        }
    }
}

@Composable
fun ToLoginText(onNavigate: (Int) -> Unit) {
    Text(
        "ログインする",
        modifier = Modifier
            .clickable(onClick = { onNavigate(R.id.loginFragment) })
            .fillMaxWidth(),
        textAlign = TextAlign.Center
    )
}