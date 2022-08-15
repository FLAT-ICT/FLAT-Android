package com.websarva.wings.android.flat.ui.startup.signUp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.websarva.wings.android.flat.R
import com.websarva.wings.android.flat.ui.startup.components.ConfirmButton
import com.websarva.wings.android.flat.ui.startup.components.PasswordTextField
import com.websarva.wings.android.flat.ui.startup.components.TextFieldWithErrorState
import com.websarva.wings.android.flat.ui.theme.FLATTheme

@Composable
fun SignUpScreen(onNavigate: (Int) -> Unit) {
    FLATTheme {
        Surface() {
            Column(modifier = Modifier.fillMaxSize()) {
                // Text: アカウントを登録しましょう
                Spacer(modifier = Modifier.padding(top = 25.dp))
                Text(text = "アカウントを登録しましょう", Modifier.padding(start = 24.dp))
                // CustomTextField: ユーザー名
                Spacer(modifier = Modifier.padding(top = 48.dp))
                TextFieldWithErrorState()
                // CustomTextField: パスワード
                Spacer(modifier = Modifier.padding(top = 18.dp))
                PasswordTextField()
                // CustomTextField: パスワード (確認)
                Spacer(modifier = Modifier.padding(top = 18.dp))
                PasswordTextField()
                // Button: 登録
                Spacer(modifier = Modifier.padding(top = 48.dp))
                ConfirmButton()
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
            .clickable(onClick = { onNavigate(R.id.accountRegistrationFragment) })
            .fillMaxWidth(),
        textAlign = TextAlign.Center
    )
}