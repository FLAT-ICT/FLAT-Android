package com.websarva.wings.android.flat.ui.startup.components

import android.widget.Button
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.websarva.wings.android.flat.R
import com.websarva.wings.android.flat.ui.parts.MidiumRoundedButton
import com.websarva.wings.android.flat.ui.theme.FLATTheme

@Composable
fun LoginAndSignUpButtons(
    onNavigate: (Int) -> Unit
/*navController: NavController*/) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 32.dp, end = 32.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        LoginButton(onNavigate)
        SignUpButton(onNavigate)
    }

}

@Composable
fun LoginButton(onNavigate: (Int) -> Unit) {
    MidiumRoundedButton(
        text = "ログイン",
        onClick = { onNavigate(R.id.loginFragment)},
        colors = ButtonDefaults.textButtonColors(
            backgroundColor = Color.White,
            contentColor = Color.Black
        ) )
}


@Composable
fun SignUpButton(onNavigate: (Int) -> Unit) {
    MidiumRoundedButton(
        text="新規登録",
        onClick = { onNavigate(R.id.accountRegistrationFragment) },
        colors=ButtonDefaults.textButtonColors(
            backgroundColor = FLATTheme.colors.primary,
            contentColor = Color.White
    ),)
}

