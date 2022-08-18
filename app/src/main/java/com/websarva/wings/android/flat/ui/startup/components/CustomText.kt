package com.websarva.wings.android.flat.ui.startup.components

import androidx.compose.foundation.clickable
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import com.websarva.wings.android.flat.R
import com.websarva.wings.android.flat.ui.theme.FLATTheme


@Composable
fun NavigationText(text: String, onClick: () -> Unit) {
    Text(
        text = text,
        style = MaterialTheme.typography.caption,
        modifier = Modifier
            .clickable(
                onClick = onClick,
                role = Role.Button
            ),
        textAlign = TextAlign.Center,
        color = FLATTheme.colors.primary,
    )
}

@Composable
fun ToSignUpText(onNavigate: (Int) -> Unit) {
    NavigationText(
        text = "アカウントを登録していない人は登録する",
        onClick = { onNavigate(R.id.accountRegistrationFragment) }
    )
}

@Composable
fun ToLoginText(onNavigate: (Int) -> Unit) {
    NavigationText(
        text = "アカウントを持っている人はログインする",
        onClick = { onNavigate(R.id.loginFragment) }
    )
}