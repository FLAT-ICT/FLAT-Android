package com.websarva.wings.android.flat.ui.startup.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ButtonDefaults.textButtonColors
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.websarva.wings.android.flat.ui.theme.FLATTheme

@Composable
fun Buttons() {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 32.dp, end = 32.dp),
        horizontalArrangement = Arrangement.SpaceBetween
            ){
        LoginButton()
        SignUpButton()
    }

}

@Composable
fun LoginButton() {

    Button(
        onClick = { /*TODO*/ },
        shape = RoundedCornerShape(20.dp),
//        colors = FLATTheme.colors.primary
        colors = textButtonColors(
            backgroundColor = Color.White,
            contentColor = Color.Black
        ),
        modifier = Modifier.size(width = 140.dp, height = 40.dp),
        elevation = ButtonDefaults.elevation()
    ) {
        Text("ログイン")
    }
}


@Composable
fun SignUpButton() {
    Button(
        onClick = { /*TODO*/ },
        shape = RoundedCornerShape(20.dp),
        colors = textButtonColors(
                backgroundColor = FLATTheme.colors.primary,
                contentColor = Color.White
            ),
        modifier = Modifier.size(width = 140.dp, height = 40.dp),
        elevation = ButtonDefaults.elevation()

        ){
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
@Preview
@Composable
fun PreviewButtons() {
    Buttons()
}