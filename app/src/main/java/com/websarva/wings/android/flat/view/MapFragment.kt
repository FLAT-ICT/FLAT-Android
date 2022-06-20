package com.websarva.wings.android.flat.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.google.android.material.composethemeadapter.MdcTheme
import com.websarva.wings.android.flat.R

class MyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // ...

        val greeting = findViewById<ComposeView>(R.id.map)
        greeting.setContent {
            MdcTheme { // or AppCompatTheme
                Greeting()
            }
        }
    }
}

@Composable
private fun Greeting() {
    Text(
        text = "Hello, world!",
        style = MaterialTheme.typography.h5,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(Alignment.CenterHorizontally)
    )
}
