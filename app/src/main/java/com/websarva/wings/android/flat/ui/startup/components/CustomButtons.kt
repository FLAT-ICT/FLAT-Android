package com.websarva.wings.android.flat.ui.startup.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.websarva.wings.android.flat.R
import com.websarva.wings.android.flat.ui.parts.MidiumRoundedButton
import com.websarva.wings.android.flat.ui.theme.FLATTheme
import kotlinx.coroutines.launch

@Composable
fun LoginAndSignUpButtons(
    onNavigate: (Int) -> Unit
/*navController: NavController*/
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 32.dp, end = 32.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        LoginButton(onNavigate)
        SignUpButton(onNavigate)
    }

}

@Composable
fun LoginButton(onNavigate: (Int) -> Unit) {
    MidiumRoundedButton(
        text = "ログイン",
        onClick = { onNavigate(R.id.loginFragment) },
        colors = ButtonDefaults.textButtonColors(
            backgroundColor = Color.White,
            contentColor = Color.Black
        )
    )
}


@Composable
fun SignUpButton(onNavigate: (Int) -> Unit) {
    MidiumRoundedButton(
        text = "新規登録",
        onClick = { onNavigate(R.id.accountRegistrationFragment) },
        colors = ButtonDefaults.textButtonColors(
            backgroundColor = FLATTheme.colors.primary,
            contentColor = Color.White
        ),
    )
}


@Composable
fun ConfirmButton(
    onCLick: ((Int) -> Unit) -> Unit,
    enabled: Boolean,
    labelResId: Int,
    errorMessageId: Int,
    setErrorMessageId: (Int) -> Unit
) {

    var isBusy by rememberSaveable { mutableStateOf(false) }
//    val (errorMessage, setErrorMessage) = remember { mutableStateOf(R.id.empty_error_message) }
    val scope = rememberCoroutineScope()

    Column() {
        Button(
            onClick = {
                isBusy = true
                scope.launch { onCLick(setErrorMessageId) }
                isBusy = false
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 24.dp, end = 24.dp
                ),
            colors = ButtonDefaults.textButtonColors(
                backgroundColor = FLATTheme.colors.primary,
                contentColor = Color.White
            ),
            enabled = enabled && !isBusy,
        ) {
            if (isBusy) {
                CircularProgressIndicator(
                    strokeWidth = 2.dp,
                    modifier = Modifier.size(24.dp)
                )
            } else {
                Text(stringResource(labelResId))
            }

        }
        // エラーメッセージ出力用
        Text(text = stringResource(errorMessageId))
    }
}

//data class SignUpState(
//    val isLoading: Boolean = false,
//    val isError: Boolean = false,
//    val errorMessage: String = ""
//)
//
//data class LoginState(
//    val preLoginState: PreLoginState = PreLoginState(),
//    val isShowDialog: Boolean = false,
//    val isLoading: Boolean = false,
//    val isError: Boolean = false,
//    var errorMessage: String = ""
//)
//
//data class PreLoginState(
//    val required: Boolean = false,
//    val done: Boolean = false,
//)

//@Composable
//fun LoginConfirmButton(enabled: Boolean) {
//    val viewModel: LoginViewModel = viewModel()
//    val state by produceState(initialValue = LoginState()) {
//        viewModel.login(LoginViewModel.LoginInput("a", "a", true, true))
//    }
//    ConfirmButton(onCLick = {
//        when {
//            state.isError -> {
//                state.errorMessage = ""
//            }
//        }
//    }, enabled = enabled, labelResId = R.string.login)
//}