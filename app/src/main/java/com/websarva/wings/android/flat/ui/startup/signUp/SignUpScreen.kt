package com.websarva.wings.android.flat.ui.startup.signUp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.websarva.wings.android.flat.ui.theme.FLATTheme

@Composable
fun SignUpScreen() {
    FLATTheme {
        Surface() {
            Column(modifier = Modifier.fillMaxSize()) {
                // Text: アカウントを登録しましょう
                // CustomTextBox: ユーザー名
                // CustomTextBox: パスワード
                // CustomTextBox: パスワード（確認）
                // Button: 登録する
                // Text: エラー
                // Text: アカウントを持っている人はログインする
            }
        }
    }
}
