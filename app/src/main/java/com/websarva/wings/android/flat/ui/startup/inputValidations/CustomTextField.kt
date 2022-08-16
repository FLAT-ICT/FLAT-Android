package com.websarva.wings.android.flat.ui.startup.inputValidations

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
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
    onImeKeyAction: OnImeKeyAction

) {
    val fieldValue = remember {
        mutableStateOf(TextFieldValue(inputWrapper.value, TextRange(inputWrapper.value.length)))
    }
    Column {
        TextField(
            modifier = modifier,
            value = fieldValue.value,
            onValueChange = {
                fieldValue.value = it
                onValueChange(it.text)
            },
            label = { Text(stringResource(labelResId)) },
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
            )
        )
        if (inputWrapper.errorId != null) {
            Text(
                text = stringResource(inputWrapper.errorId),
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
}