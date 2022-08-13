package com.websarva.wings.android.flat.ui.startup

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.websarva.wings.android.flat.ui.startup.components.Buttons
import com.websarva.wings.android.flat.ui.theme.FLATTheme

@Composable
fun StartupScreen(){
    FLATTheme {
        Spacer(modifier = Modifier.absoluteOffset())
        Buttons()
    }
}