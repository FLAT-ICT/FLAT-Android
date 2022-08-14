package com.websarva.wings.android.flat.ui.startup

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.websarva.wings.android.flat.ui.startup.components.Buttons
import com.websarva.wings.android.flat.ui.theme.FLATTheme

@Composable
fun StartupScreen(){
    FLATTheme {
        Surface() {
            Column(modifier = Modifier.fillMaxSize()) {
                Spacer(Modifier.height(120.dp))
                Text(
                    text = "FLAT",
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                )
                Divider(
                    color=FLATTheme.colors.primary,
                    thickness = 4.dp,
                    modifier = Modifier.padding(start = 70.dp, end = 70.dp))
                Spacer(Modifier.weight(1f))
                Buttons()
                Spacer(Modifier.padding(vertical = 16.dp))
            }
        }
    }
}

@Preview
@Composable
fun DefaultPreview(){
    StartupScreen()
}