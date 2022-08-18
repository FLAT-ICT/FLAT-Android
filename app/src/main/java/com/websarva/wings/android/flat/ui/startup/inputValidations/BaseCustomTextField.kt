package com.websarva.wings.android.flat.ui.startup.inputValidations

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.websarva.wings.android.flat.ui.theme.FLATTheme

@Composable
fun CustomTextField(
    modifier: Modifier,
    inputWrapper: InputWrapper,
    @StringRes labelResId: Int,
    keyboardOptions: KeyboardOptions = remember {
        KeyboardOptions.Default
    },
    visualTransformation: VisualTransformation = remember {
        VisualTransformation.None
    },
    onValueChange: (String) -> Unit,
    onImeKeyAction: OnImeKeyAction,
    trailingIcon: @Composable() (() -> Unit)? = null,
) {
    val fieldValue = remember {
        mutableStateOf(TextFieldValue(inputWrapper.value, TextRange(inputWrapper.value.length)))
    }
    Column {
        Text(
            modifier = modifier,
            text = stringResource(labelResId),
            style = MaterialTheme.typography.caption
        )
        OutlinedTextField(
            modifier = modifier,
            value = fieldValue.value,
            onValueChange = {
                fieldValue.value = it
                onValueChange(it.text)
            },
//            label = { Text(stringResource(labelResId)) },
            isError = inputWrapper.errorId != null,
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            keyboardActions = remember {
                KeyboardActions(onAny = { onImeKeyAction() })
            },
            colors = TextFieldDefaults.textFieldColors(
                textColor = if (inputWrapper.errorId != null) Color.Red else Color.Black,
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = FLATTheme.colors.primary,
                focusedLabelColor = FLATTheme.colors.primary,
                cursorColor = FLATTheme.colors.primary,
            ),
            trailingIcon = trailingIcon,
            singleLine = true,
        )
        Text(
            text = if (inputWrapper.errorId != null) stringResource(inputWrapper.errorId) else "",
            color = MaterialTheme.colors.error,
            style = MaterialTheme.typography.caption,
            modifier = Modifier.padding(start = 24.dp)
        )
    }
}