package com.websarva.wings.android.flat.ui.startup.login_signup

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable

@Composable
fun ForceLoginDialog(
    isOpen: Boolean,
    setIsOpen: (Boolean) -> Unit,
    onConfirm: () -> Unit
) {
    if (isOpen) {
        AlertDialog(
            text = { Text(text = "他の端末でログインしています．ログインしますか？（他の端末からはログアウトされます）") },
            onDismissRequest = {
                setIsOpen(false)
            },
            confirmButton = {
                TextButton(onClick = {
                    onConfirm()
                    setIsOpen(false)
                }) {
                    Text(text = "ログイン")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    setIsOpen(false)
                }) {
                    Text(text = "キャンセル")
                }
            }
        )
    }
}