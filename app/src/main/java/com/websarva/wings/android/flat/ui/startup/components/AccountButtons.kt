package com.websarva.wings.android.flat.ui.startup.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.websarva.wings.android.flat.ui.theme.FLATTheme

@Composable
fun Buttons() {

}

@Composable
fun LoginButton() {

    Button(
        onClick = { /*TODO*/ },
        shape = RoundedCornerShape(4.dp),
//        colors = FLATTheme.colors.primary
        colors = FLATTheme.colors.primary
    ) {
        Text("ログイン")
    }
}

@Composable
fun RegisterButton() {
    Button(onClick = { /*TODO*/ }) {
        Text("新規登録")
    }
}

//@Composable
//fun CustomButton(f: () -> Unit, text: String) {
//    Button(
//        onClick = { f },
//        elevation = 0.dp,
//
//        ) {
//
//    }
//}