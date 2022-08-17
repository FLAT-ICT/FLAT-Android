package com.websarva.wings.android.flat.ui.startup.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.websarva.wings.android.flat.R
import com.websarva.wings.android.flat.ui.startup.inputValidations.CustomTextField
import com.websarva.wings.android.flat.ui.startup.inputValidations.InputWrapper
import com.websarva.wings.android.flat.ui.startup.inputValidations.OnImeKeyAction
import com.websarva.wings.android.flat.ui.startup.inputValidations.OnValueChange

@Composable
fun NameTextField(
    focusRequester: FocusRequester,
    onFocusChanged: (FocusState) -> Unit,
    inputWrapper: InputWrapper,
    onValueChange: OnValueChange,
    onImeAction: OnImeKeyAction
) {
    CustomTextField(
        modifier = Modifier
            .focusRequester(focusRequester)
            .onFocusChanged {
                onFocusChanged(it)
            }
            .fillMaxWidth()
            .padding(
                start = 24.dp, end = 24.dp
            ),
        labelResId = R.string.input_nickname_for_login,
        keyboardOptions = remember {
            KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            )
        },
        onValueChange = onValueChange,
        onImeKeyAction = onImeAction,
        inputWrapper = inputWrapper
    )
}

@Composable
fun PasswordTextField(
    focusRequester: FocusRequester,
    onFocusChanged: (FocusState) -> Unit,
    inputWrapper: InputWrapper,
    onValueChange: OnValueChange,
    onImeAction: OnImeKeyAction
) {
    var passwordHidden by rememberSaveable { mutableStateOf(true) }
    CustomTextField(
        modifier = Modifier
            .focusRequester(focusRequester)
            .onFocusChanged {
                onFocusChanged(it)
            }
            .fillMaxWidth()
            .padding(
                start = 24.dp, end = 24.dp
            ),
        labelResId = R.string.input_password_for_login,
        keyboardOptions = remember {
            KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next
            )
        },
        visualTransformation =
        if (passwordHidden) PasswordVisualTransformation() else VisualTransformation.None,
        trailingIcon = {
            IconButton(onClick = { passwordHidden = !passwordHidden }) {
                val visibilityIcon =
                    if (passwordHidden) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                // Please provide localized description for accessibility services
                val description = if (passwordHidden) "Show password" else "Hide password"
                Icon(imageVector = visibilityIcon, contentDescription = description)
            }
        },
        inputWrapper = inputWrapper,
        onValueChange = onValueChange,
        onImeKeyAction = onImeAction
    )
}

@Composable
fun PasswordRetypeTextField(
    nameFocusRequester: FocusRequester,
    onFocusChanged: (FocusState) -> Unit,
    inputWrapper: InputWrapper,
    onValueChange: OnValueChange,
    onImeAction: OnImeKeyAction
) {
    var passwordHidden by rememberSaveable { mutableStateOf(true) }
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

        inputWrapper = inputWrapper,
        labelResId = R.string.name,
        keyboardOptions = remember {
            KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            )
        },
        onValueChange = onValueChange,
        onImeKeyAction = onImeAction,
        trailingIcon = {
            IconButton(onClick = { passwordHidden = !passwordHidden }) {
                val visibilityIcon =
                    if (passwordHidden) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                // Please provide localized description for accessibility services
                val description = if (passwordHidden) "Show password" else "Hide password"
                Icon(imageVector = visibilityIcon, contentDescription = description)
            }
        }
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