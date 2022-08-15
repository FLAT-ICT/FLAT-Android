package com.websarva.wings.android.flat.ui.startup.signUp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.websarva.wings.android.flat.R
import com.websarva.wings.android.flat.ui.theme.FLATTheme

@Composable
fun SignUpScreen(onNavigate: (Int) -> Unit) {
    FLATTheme {
        Surface() {
            Column(modifier = Modifier.fillMaxSize()) {
                // Text: アカウントを登録しましょう
                Text(
                    text = "アカウントを登録しましょう"
                )
                // CustomTextBox: ユーザー名
                TextFieldWithErrorState()
                // CustomTextBox: パスワード
                PasswordTextField()
                // CustomTextBox: パスワード（確認）
                PasswordTextField()
                // Button: 登録する
                ConfirmButton()
                // Text: アカウントを持っている人はログインする
                ToLoginText(onNavigate)
            }
        }
    }
}

@Composable
fun TextFieldWithErrorState() {
    var text by rememberSaveable { mutableStateOf("") }
    var isError by rememberSaveable { mutableStateOf(false) }

    fun validate(text: String) {
        isError = text.count() < 5
    }

    TextField(
        value = text,
        onValueChange = {
            text = it
            isError = false
        },
        singleLine = true,
        label = { Text(if (isError) "UserName*" else "UserName") },
        isError = isError,
        keyboardActions = KeyboardActions { validate(text) },
        modifier = Modifier.semantics {
            // Provide localized description of the error
            if (isError) error("Email format is invalid.")
        }
    )
}


@Composable
fun PasswordTextField() {
    var password by rememberSaveable { mutableStateOf("") }
    var passwordHidden by rememberSaveable { mutableStateOf(true) }
    TextField(
        value = password,
        onValueChange = { password = it },
        singleLine = true,
        label = { Text("Enter password") },
        visualTransformation =
        if (passwordHidden) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
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

@Composable
fun ConfirmButton() {
    var isConfirmed by rememberSaveable { mutableStateOf(false) }
    Button(onClick = { isConfirmed = true }) {
        Text("Confirm")
    }
}

@Composable
fun ToLoginText(onNavigate: (Int) -> Unit) {
    Text("ログインする", modifier = Modifier.clickable(onClick = { onNavigate(R.id.loginFragment) }))
}