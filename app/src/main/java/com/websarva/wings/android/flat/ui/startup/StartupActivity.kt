//package com.websarva.wings.android.flat.ui.startup
//
//import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.navigation.compose.NavHost
//import androidx.navigation.compose.composable
//import androidx.navigation.compose.rememberNavController
//import com.websarva.wings.android.flat.ui.startup.accountRegistration.AccountRegistrationScreen
//
//
////class StartupActivity : AppCompatActivity() {
////    override fun onCreate(savedInstanceState: Bundle?) {
////        super.onCreate(savedInstanceState)
////        setContentView(R.layout.activity_startup)
////    }
////}
//
//class StartupActivity : ComponentActivity(){
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            val navController = rememberNavController()
//            NavHost(navController = navController, startDestination = "startup"){
//                composable("startup"){ StartupScreen(navController)}
////                composable("login"){ LoginScreen(navController)}
//                composable("register"){ AccountRegistrationScreen(navController) }
//            }
////            StartupScreen(navController)
//        }
//    }
//}
