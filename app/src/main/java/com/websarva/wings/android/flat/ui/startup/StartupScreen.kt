package com.websarva.wings.android.flat.ui.startup

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.ButtonDefaults.textButtonColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.websarva.wings.android.flat.R
import com.websarva.wings.android.flat.ui.theme.FLATTheme

@Composable
fun StartupScreen(
    onNavigate: (Int) -> Unit
/*navController: NavController*/
){
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
                Buttons(
                    onNavigate = onNavigate
                /*navController*/)
                Spacer(Modifier.padding(vertical = 16.dp))
            }
        }
    }
}

@Composable
fun Buttons(
    onNavigate: (Int) -> Unit
/*navController: NavController*/) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 32.dp, end = 32.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        LoginButton(onNavigate)
        SignUpButton(onNavigate)
    }

}

@Composable
fun LoginButton(onNavigate: (Int) -> Unit) {

    Button(
        onClick = {
                  /*TODO*/
                    onNavigate(R.id.loginFragment)
                  },
        shape = RoundedCornerShape(20.dp),
//        colors = FLATTheme.colors.primary
        colors = textButtonColors(
            backgroundColor = Color.White,
            contentColor = Color.Black
        ),
        modifier = Modifier.size(width = 140.dp, height = 40.dp),
        elevation = ButtonDefaults.elevation()
    ) {
        Text("ログイン")
    }
}


@Composable
fun SignUpButton(
    onNavigate: (Int) -> Unit
    /*navController: NavController*/) {
    Button(
        onClick = { onNavigate(R.id.accountRegistrationFragment) },
        shape = RoundedCornerShape(20.dp),
        colors = textButtonColors(
            backgroundColor = FLATTheme.colors.primary,
            contentColor = Color.White
        ),
        modifier = Modifier.size(width = 140.dp, height = 40.dp),
        elevation = ButtonDefaults.elevation()

    ){
        Text("新規登録")
    }
}

//@Preview
//@Composable
//fun DefaultPreview(){
//    StartupScreen(/*navController = rememberNavController()*/)
//}