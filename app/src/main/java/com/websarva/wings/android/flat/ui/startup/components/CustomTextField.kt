package com.websarva.wings.android.flat.ui.startup.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.websarva.wings.android.flat.ui.theme.FLATTheme

@Composable
fun NameTextField(
    text: String,
    isError: Boolean,
    onErrorChange: (Boolean) -> Unit,
    onValueChange: (String) -> Unit
) {
    fun validate(text: String) {
        onErrorChange(text.isEmpty() || text.length >= 10)
    }


    TextField(
        value = text,
        onValueChange = onValueChange,
        singleLine = true,
        label = { Text(text = if (isError) "UserName*" else "UserName") },
        isError = isError,
        keyboardActions = KeyboardActions { validate(text) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp, end = 24.dp),
        colors = TextFieldDefaults.textFieldColors(
            textColor = if (isError) Color.Red else Color.Black,
            backgroundColor = Color.Transparent,
            focusedIndicatorColor = FLATTheme.colors.primary,
            focusedLabelColor = FLATTheme.colors.primary,
            cursorColor = FLATTheme.colors.primary,
        )
    )
}


@Composable
fun PasswordTextField(
    password: String,
    isError: Boolean,
    onValueChange: (String) -> Unit,
    onErrorChange: (Boolean) -> Unit
) {

    fun validate(text: String) {
        onErrorChange(text.length !in 8..255)
    }

    var passwordHidden by rememberSaveable { mutableStateOf(true) }
    TextField(
        value = password,
        onValueChange = onValueChange,
        singleLine = true,
        label = { Text("Enter password") },
        visualTransformation =
        if (passwordHidden) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        keyboardActions = KeyboardActions { validate(password) },
        trailingIcon = {
            IconButton(onClick = { passwordHidden = !passwordHidden }) {
                val visibilityIcon =
                    if (passwordHidden) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                // Please provide localized description for accessibility services
                val description = if (passwordHidden) "Show password" else "Hide password"
                Icon(imageVector = visibilityIcon, contentDescription = description)
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 24.dp, end = 24.dp
            ),
        colors = TextFieldDefaults.textFieldColors(
            textColor = if (isError) Color.Red else Color.Black,
            backgroundColor = Color.Transparent,
            focusedIndicatorColor = FLATTheme.colors.primary,
            focusedLabelColor = FLATTheme.colors.primary,
            cursorColor = FLATTheme.colors.primary,
        )
    )
}