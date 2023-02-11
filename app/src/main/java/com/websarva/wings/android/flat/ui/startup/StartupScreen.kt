package com.websarva.wings.android.flat.ui.startup

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.ButtonDefaults.textButtonColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.websarva.wings.android.flat.R
import com.websarva.wings.android.flat.ui.startup.components.LoginAndSignUpButtons
import com.websarva.wings.android.flat.ui.theme.FLATTheme
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun StartupScreen(
    onNavigate: (Int) -> Unit,
    model: StartupViewModel = viewModel()
/*navController: NavController*/
) {
    /*
    * 画面の出し分けとして．
    * viewModel内のisReady=falseのとき
    * 1. ローディング画面(FLATというタイトルのみ)を表示する
    *
    * viewModel内のisReady=trueのときに
    * 2. Roomにユーザー情報があれば．ホーム画面へ遷移する
    * 3. 情報がなければ，ログイン/登録画面へ遷移する
    * */

    val isReady by model.isReady.collectAsState()
    val user by model.user.collectAsState()

    if (isReady) {
        if (user != null && user!!.myId != -1) {
            onNavigate(R.id.friendListFragment)
        } else {
            LoginAndSignUpScreen(onNavigate)
        }
    }else{
        TitleScreen()
    }
}


@Composable
fun TitleScreen(){
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
                    color = FLATTheme.colors.primary,
                    thickness = 4.dp,
                    modifier = Modifier.padding(start = 70.dp, end = 70.dp)
                )
            }
        }
    }
}


@Composable
fun LoginAndSignUpScreen(onNavigate: (Int) -> Unit) {
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
                    color = FLATTheme.colors.primary,
                    thickness = 4.dp,
                    modifier = Modifier.padding(start = 70.dp, end = 70.dp)
                )
                Spacer(Modifier.weight(1f))
                LoginAndSignUpButtons(
                    onNavigate = onNavigate
                    /*navController*/
                )
                Spacer(Modifier.padding(vertical = 16.dp))
            }
        }
    }
}

//@Preview
//@Composable
//fun DefaultPreview(){
//    StartupScreen(/*navController = rememberNavController()*/)
//}