package com.websarva.wings.android.flat.ui.startup.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.websarva.wings.android.flat.R
import com.websarva.wings.android.flat.ui.parts.MidiumRoundedButton
import com.websarva.wings.android.flat.ui.theme.FLATTheme

@Composable
fun LoginAndSignUpButtons(
    onNavigate: (Int) -> Unit
/*navController: NavController*/
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 32.dp, end = 32.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        LoginButton(onNavigate)
        SignUpButton(onNavigate)
    }

}

@Composable
fun LoginButton(onNavigate: (Int) -> Unit) {
    MidiumRoundedButton(
        text = "ログイン",
        onClick = { onNavigate(R.id.loginFragment) },
        colors = ButtonDefaults.textButtonColors(
            backgroundColor = Color.White,
            contentColor = Color.Black
        )
    )
}


@Composable
fun SignUpButton(onNavigate: (Int) -> Unit) {
    MidiumRoundedButton(
        text = "新規登録",
        onClick = { onNavigate(R.id.accountRegistrationFragment) },
        colors = ButtonDefaults.textButtonColors(
            backgroundColor = FLATTheme.colors.primary,
            contentColor = Color.White
        ),
    )
}


@Composable
fun ConfirmButton(flags: List<Boolean>) {
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
        ),
        enabled = flags.all { it }
    ) {
        Text("Confirm")
    }
}
