package com.websarva.wings.android.flat.ui.startup.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.websarva.wings.android.flat.R
import com.websarva.wings.android.flat.ui.startup.components.ConfirmButton
import com.websarva.wings.android.flat.ui.startup.components.NameTextField
import com.websarva.wings.android.flat.ui.startup.components.PasswordTextField
import com.websarva.wings.android.flat.ui.theme.FLATTheme

@Composable
fun LoginScreen(onNavigate: (Int) -> Unit) {


    var name by remember {
        mutableStateOf("")
    }
    var pass1 by remember {
        mutableStateOf("")
    }
    var isInvalidName by remember {
        mutableStateOf(false)
    }
    var isInvalidPass1 by remember {
        mutableStateOf(false)
    }
    var isMatchPassword by remember {
        mutableStateOf(false)
    }

    var isPushable by remember {
        mutableStateOf(!isInvalidName && !isInvalidPass1)
    }

    FLATTheme {
        Surface() {
            Column(modifier = Modifier.fillMaxSize()) {
                // Text: ログイン画面 // これ多分別のテキスト
                Spacer(modifier = Modifier.padding(top = 25.dp))
                Text(text = "ログイン", Modifier.padding(start = 24.dp))
                // CustomTextField: ユーザー名
                Spacer(modifier = Modifier.padding(top = 48.dp))
                NameTextField(
                    text = name,
                    isError = isInvalidName,
                    onValueChange = { name = it },
                    onErrorChange = { isInvalidName = it }
                )
                // CustomTextField: パスワード
                Spacer(modifier = Modifier.padding(top = 18.dp))
                PasswordTextField(
                    password = pass1,
                    isError = isInvalidPass1,
                    onValueChange = { pass1 = it },
                    onErrorChange = { isInvalidPass1 = it }
                )
                // Button: ログイン
                Spacer(modifier = Modifier.padding(top = 48.dp))
                ConfirmButton(flags = listOf(!isInvalidName, !isInvalidPass1))
                // Text: アカウントを新規登録する
                Spacer(modifier = Modifier.weight(1f))
                ToSignUpText(onNavigate)
                Spacer(modifier = Modifier.padding(top = 32.dp))
            }
        }
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