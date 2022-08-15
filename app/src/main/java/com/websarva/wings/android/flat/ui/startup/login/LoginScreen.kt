package com.websarva.wings.android.flat.ui.startup.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.websarva.wings.android.flat.ui.theme.FLATTheme

@Composable
fun LoginScreen() {
    FLATTheme {
        Surface() {
            Column(modifier = Modifier.fillMaxSize()) {
                // Text: ログイン画面 // これ多分別のテキスト
                // CustomTextField: ユーザー名
                // CustomTextField: パスワード
                // Button: ログイン
                // Text: アカウントを新規登録する
            }
        }
    }
}