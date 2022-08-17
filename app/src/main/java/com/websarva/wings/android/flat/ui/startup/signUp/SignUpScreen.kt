package com.websarva.wings.android.flat.ui.startup.signUp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.websarva.wings.android.flat.R
import com.websarva.wings.android.flat.ui.theme.FLATTheme

@Composable
fun SignUpScreen(onNavigate: (Int) -> Unit) {

    var name by remember {
        mutableStateOf("")
    }
    var pass1 by remember {
        mutableStateOf("")
    }
    var pass2 by remember {
        mutableStateOf("")
    }
    var isInvalidName by remember {
        mutableStateOf(false)
    }
    var isInvalidPass1 by remember {
        mutableStateOf(false)
    }
    var isInvalidPass2 by remember {
        mutableStateOf(false)
    }
    var isMatchPassword by remember {
        mutableStateOf(false)
    }

    FLATTheme {
        Surface() {
            Column(modifier = Modifier.fillMaxSize()) {
                // Text: アカウントを登録しましょう
                Spacer(modifier = Modifier.padding(top = 25.dp))
                Text(text = "アカウントを登録しましょう", Modifier.padding(start = 24.dp))
                // CustomTextField: ユーザー名
                Spacer(modifier = Modifier.padding(top = 48.dp))
//                NameTextField(
//                    text = name,
//                    isError = isInvalidName,
//                    onValueChange = { name = it },
//                    onErrorChange = { isInvalidName = it })
//                // CustomTextField: パスワード
//                Spacer(modifier = Modifier.padding(top = 18.dp))
//                PasswordTextField(
//                    password = pass1,
//                    isError = isInvalidPass1,
//                    onValueChange = { pass1 = it },
//                    onErrorChange = { isInvalidPass1 = it })
//                // CustomTextField: パスワード (確認)
//                Spacer(modifier = Modifier.padding(top = 18.dp))
//                PasswordTextField(
//                    password = pass2,
//                    isError = isInvalidPass2,
//                    onValueChange = { pass2 = it },
//                    onErrorChange = { isInvalidPass2 = it })
                // Button: 登録
                Spacer(modifier = Modifier.padding(top = 48.dp))
//                ConfirmButton(
//                    flags = listOf(
//                        !isInvalidName,
//                        !isInvalidPass1,
//                        !isInvalidPass2,
//                        pass1 == pass2
//                    ),
//                )
                // Text: アカウントを新規登録する
                Spacer(modifier = Modifier.weight(1f))
                // Text: アカウントを持っている人はログインする
                ToLoginText(onNavigate)
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