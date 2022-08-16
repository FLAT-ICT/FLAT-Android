package com.websarva.wings.android.flat.ui.parts

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MidiumRoundedButton(text: String, onClick: () -> Unit, colors: ButtonColors) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(20.dp),
        colors = colors,
        modifier = Modifier.size(width = 140.dp, height = 40.dp),
        elevation = ButtonDefaults.elevation()
    ){
        Text(text)
    }
}