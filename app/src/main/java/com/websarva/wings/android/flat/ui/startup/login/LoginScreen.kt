package com.websarva.wings.android.flat.ui.startup.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.websarva.wings.android.flat.R
import com.websarva.wings.android.flat.ui.theme.FLATTheme

@Composable
fun LoginScreen(onNavigate: (Int) -> Unit) {
    FLATTheme {
        Surface() {
            Column(modifier = Modifier.fillMaxSize()) {
                // Text: ログイン画面 // これ多分別のテキスト
                Spacer(modifier = Modifier.padding(top = 25.dp))
                Text(text = "ログイン", Modifier.padding(start = 24.dp))
                // CustomTextField: ユーザー名
                Spacer(modifier = Modifier.padding(top = 48.dp))
                TextFieldWithErrorState()
                // CustomTextField: パスワード
                Spacer(modifier = Modifier.padding(top = 18.dp))
                PasswordTextField()
                // Button: ログイン
                Spacer(modifier = Modifier.padding(top = 48.dp))
                ConfirmButton()
                // Text: アカウントを新規登録する
                Spacer(modifier = Modifier.weight(1f))
                ToSignUpText(onNavigate)
                Spacer(modifier = Modifier.padding(top = 32.dp))
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
        modifier = Modifier
//            .semantics {
//                // Provide localized description of the error
//                if (isError) error("Email format is invalid.")
//            }
            .fillMaxWidth()
            .padding(start = 24.dp, end = 24.dp),
        colors = TextFieldDefaults.textFieldColors(
            textColor = if (isError) Color.Red else Color.Black,
            backgroundColor = Color.Transparent,
            focusedIndicatorColor = FLATTheme.colors.primary,
            focusedLabelColor = FLATTheme.colors.primary,
            cursorColor = FLATTheme.colors.primary,
        )
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
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 24.dp, end = 24.dp
            ),
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.Black,
            backgroundColor = Color.Transparent,
            focusedIndicatorColor = FLATTheme.colors.primary,
            focusedLabelColor = FLATTheme.colors.primary,
            cursorColor = FLATTheme.colors.primary,
        )
    )
}

@Composable
fun ConfirmButton() {
    var isConfirmed by rememberSaveable { mutableStateOf(false) }
    Button(
        onClick = { isConfirmed = true },
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 24.dp, end = 24.dp
            ),
        colors = ButtonDefaults.textButtonColors(
            backgroundColor = FLATTheme.colors.primary,
            contentColor = Color.White
        )
    ) {
        Text("Confirm")
    }
}

@Composable
fun ToSignUpText(onNavigate: (Int) -> Unit) {
    Text(
        "アカウントを新規登録する",
        textAlign = TextAlign.Center,
        modifier = Modifier
            .clickable(onClick = { onNavigate(R.id.accountRegistrationFragment) })
            .fillMaxWidth(),
    )
}

//@Preview
//@Composable
//fun PreviewLoginScreen() {
//    LoginScreen()
//}