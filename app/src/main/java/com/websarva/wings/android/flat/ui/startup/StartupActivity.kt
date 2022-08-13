package com.websarva.wings.android.flat.ui.startup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent


//class StartupActivity : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_startup)
//    }
//}

class StartupActivity : ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { StartupScreen() }
    }
}
