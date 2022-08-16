package com.websarva.wings.android.flat.ui.startup.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import com.websarva.wings.android.flat.R
import com.websarva.wings.android.flat.ui.startup.inputValidations.*

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun InputValidationAutoScreen(viewModel: InputValidationAutoViewModel = hiltViewModel()) {
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
    val creditCardNumber by viewModel.creditCardNumber.collectAsState()
    val areInputsValid by viewModel.areInputsValid.collectAsState()

    val creditCardNumberFocusRequester = remember { FocusRequester() }
    val nameFocusRequester = remember { FocusRequester() }

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
                        FocusedTextFieldKey.CREDIT_CARD_NUMBER -> creditCardNumberFocusRequester.requestFocus()
                        else -> {}
                    }
                }
                is ScreenEvent.MoveFocus -> focusManager.moveFocus(event.direction)
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomTextField(
            modifier = Modifier
                .focusRequester(nameFocusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        key = FocusedTextFieldKey.NAME,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.name,
            keyboardOptions = remember {
                KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                )
            },
            inputWrapper = name,
            onValueChange = viewModel::onNameEntered,
            onImeKeyAction = viewModel::onNameImeActionClick
        )
        Spacer(Modifier.height(16.dp))
        CustomTextField(
            modifier = Modifier
                .focusRequester(creditCardNumberFocusRequester)
                .onFocusChanged { focusState ->
                    viewModel.onTextFieldFocusChanged(
                        key = FocusedTextFieldKey.CREDIT_CARD_NUMBER,
                        isFocused = focusState.isFocused
                    )
                },
            labelResId = R.string.credit_card_number,
            keyboardOptions = remember {
                KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                )
            },
            visualTransformation = ::creditCardFilter,
            inputWrapper = creditCardNumber,
            onValueChange = viewModel::onCardNumberEntered,
            onImeKeyAction = viewModel::onContinueClick
        )
        Spacer(Modifier.height(32.dp))
        Button(onClick = viewModel::onContinueClick, enabled = areInputsValid) {
            Text(text = "Continue")
        }
    }
}

//@Composable
//fun LoginScreen(onNavigate: (Int) -> Unit) {
//
//
//    var name by remember {
//        mutableStateOf("")
//    }
//    var pass1 by remember {
//        mutableStateOf("")
//    }
//    var isInvalidName by remember {
//        mutableStateOf(false)
//    }
//    var isInvalidPass1 by remember {
//        mutableStateOf(false)
//    }
//    var isMatchPassword by remember {
//        mutableStateOf(false)
//    }
//
//    var isPushable by remember {
//        mutableStateOf(!isInvalidName && !isInvalidPass1)
//    }
//
//    FLATTheme {
//        Surface() {
//            Column(modifier = Modifier.fillMaxSize()) {
//                // Text: ログイン画面 // これ多分別のテキスト
//                Spacer(modifier = Modifier.padding(top = 25.dp))
//                Text(text = "ログイン", Modifier.padding(start = 24.dp))
//                // CustomTextField: ユーザー名
//                Spacer(modifier = Modifier.padding(top = 48.dp))
//                NameTextField(
//                    text = name,
//                    isError = isInvalidName,
//                    onValueChange = { name = it },
//                    onErrorChange = { isInvalidName = it }
//                )
//                // CustomTextField: パスワード
//                Spacer(modifier = Modifier.padding(top = 18.dp))
//                PasswordTextField(
//                    password = pass1,
//                    isError = isInvalidPass1,
//                    onValueChange = { pass1 = it },
//                    onErrorChange = { isInvalidPass1 = it }
//                )
//                // Button: ログイン
//                Spacer(modifier = Modifier.padding(top = 48.dp))
//                ConfirmButton(flags = listOf(!isInvalidName, !isInvalidPass1))
//                // Text: アカウントを新規登録する
//                Spacer(modifier = Modifier.weight(1f))
//                ToSignUpText(onNavigate)
//                Spacer(modifier = Modifier.padding(top = 32.dp))
//            }
//        }
//    }
//}
//
//
//@Composable
//fun ToSignUpText(onNavigate: (Int) -> Unit) {
//    Text(
//        "アカウントを新規登録する",
//        textAlign = TextAlign.Center,
//        modifier = Modifier
//            .clickable(onClick = { onNavigate(R.id.accountRegistrationFragment) })
//            .fillMaxWidth(),
//    )
//}

//@Preview
//@Composable
//fun PreviewLoginScreen() {
//    LoginScreen()
//}