package com.websarva.wings.android.flat.ui.startup.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.websarva.wings.android.flat.R
import com.websarva.wings.android.flat.ui.startup.inputValidations.CustomTextField
import com.websarva.wings.android.flat.ui.startup.inputValidations.InputWrapper
import com.websarva.wings.android.flat.ui.startup.inputValidations.OnImeKeyAction
import com.websarva.wings.android.flat.ui.startup.inputValidations.OnValueChange

@Composable
fun NameTextField(
    nameFocusRequester: FocusRequester,
    onFocusChanged: (FocusState) -> Unit,
    inputWrapper: InputWrapper,
    onValueChange: OnValueChange,
    onImeAction: OnImeKeyAction
) {
    CustomTextField(
        modifier = Modifier
            .focusRequester(nameFocusRequester)
            .onFocusChanged {
                onFocusChanged(it)
            },
        labelResId = R.string.name,
        keyboardOptions = remember {
            KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            )
        },
        inputWrapper = inputWrapper,
        onValueChange = onValueChange,
        onImeKeyAction = onImeAction
    )
}
//
//CustomTextField(
//modifier = Modifier
//.focusRequester(nameFocusRequester)
//.onFocusChanged { focusState ->
//    viewModel.onTextFieldFocusChanged(
//        key = FocusedTextFieldKey.NAME,
//        isFocused = focusState.isFocused
//    )
//},
//labelResId = R.string.name,
//keyboardOptions = remember {
//    KeyboardOptions(
//        keyboardType = KeyboardType.Text,
//        imeAction = ImeAction.Next
//    )
//},
//inputWrapper = name,
//onValueChange = viewModel::onNameEntered,
//onImeKeyAction = viewModel::onNameImeActionClick
//)

@Composable
fun PasswordTextField(
    nameFocusRequester: FocusRequester,
    onFocusChanged: (FocusState) -> Unit,
    inputWrapper: InputWrapper,
    onValueChange: OnValueChange,
    onImeAction: OnImeKeyAction
) {
    CustomTextField(
        modifier = Modifier
            .focusRequester(nameFocusRequester)
            .onFocusChanged {
                onFocusChanged(it)
            }
            .fillMaxWidth()
            .padding(
                start = 24.dp, end = 24.dp
            ),
        
        labelResId = R.string.name,
        keyboardOptions = remember {
            KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            )
        },
        inputWrapper = inputWrapper,
        onValueChange = onValueChange,
        onImeKeyAction = onImeAction
    )
}

//@Composable
//fun PasswordTextField(
//    password: String,
//    isError: Boolean,
//    onValueChange: (String) -> Unit,
//    onErrorChange: (Boolean) -> Unit
//) {
//
//    fun validate(text: String) {
//        onErrorChange(text.length !in 8..255)
//    }
//
//    var passwordHidden by rememberSaveable { mutableStateOf(true) }
//    TextField(
//        value = password,
//        onValueChange = onValueChange,
//        singleLine = true,
//        label = { Text("Enter password") },
//        visualTransformation =
//        if (passwordHidden) PasswordVisualTransformation() else VisualTransformation.None,
//        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
//        keyboardActions = KeyboardActions { validate(password) },
//        trailingIcon = {
//            IconButton(onClick = { passwordHidden = !passwordHidden }) {
//                val visibilityIcon =
//                    if (passwordHidden) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
//                // Please provide localized description for accessibility services
//                val description = if (passwordHidden) "Show password" else "Hide password"
//                Icon(imageVector = visibilityIcon, contentDescription = description)
//            }
//        },
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(
//                start = 24.dp, end = 24.dp
//            ),
//        colors = TextFieldDefaults.textFieldColors(
//            textColor = if (isError) Color.Red else Color.Black,
//            backgroundColor = Color.Transparent,
//            focusedIndicatorColor = FLATTheme.colors.primary,
//            focusedLabelColor = FLATTheme.colors.primary,
//            cursorColor = FLATTheme.colors.primary,
//        )
//    )
//}