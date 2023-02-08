package com.websarva.wings.android.flat.ui.map

import com.websarva.wings.android.flat.R
import androidx.compose.foundation.Image
import android.view.Surface
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.mxalbert.zoomable.Zoomable
import com.websarva.wings.android.flat.ui.theme.FLATTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
/*
    mapの画面を作成する関数

    Mapの拡大率をStateとして持つ
    画面をピンチイン・ピンチアウトで拡大・縮小できるようにする

    1つの場所に複数のピンがある場合，現在の拡大率に応じて表示方法を変える．
    - 拡大率が閾値より小さい場合，ピンをまとめて表示する．ピンには数字を表示する．
    - 拡大率が閾値より大きい場合，ピンを個別に表示する．

    ピンは地図に対する相対表示位置を持つ．
*/
@Composable
fun MapScreen (){

    val floor = remember { mutableStateOf(1) }
    var mapScale = remember { mutableStateOf(1f) }

    val scope = rememberCoroutineScope()

    FLATTheme {
        // この中にmapの画面を作成していく
//        val state = rememberZoomableState(
//            minScale = 2f
//        )
//        Zoomable(state = state) {
//            Text(text = "Zoom me!")
//        }

        BoxWithConstraints(Modifier.fillMaxSize()) {
            val width = maxWidth
            ElevatorButton(floor, scope, width)
            Box(modifier = Modifier.fillMaxSize()) {
                when (floor.value) {
                    1 -> Map1f()
                    2 -> Map2f()
                    3 -> Map3f()
                    4 -> Map4f()
                    5 -> Map5f()
                }
            }
        }
    }
}


@Composable
fun ElevatorButton(currentFloor: MutableState<Int>, scope: CoroutineScope, parentwidth: Dp) {
    Column(
        // 右寄せ
        modifier = Modifier
            .zIndex(1f)
            // TODO:ハードコーディングがすぎるので直す
            .padding( parentwidth - 80.dp, 16.dp, 16.dp, 0.dp)
    ){
        Button(onClick = {
            scope.launch {
                currentFloor.value = 5
            }
        }) {
            Text(text = "5")
        }
        Button(onClick = {
            scope.launch {
                currentFloor.value = 4
            }
        }) {
            Text(text = "4")
        }
        Button(onClick = {
            scope.launch {
                currentFloor.value = 3
            }
        }) {
            Text(text = "3")
        }
        Button(onClick = {
            scope.launch {
                currentFloor.value = 2
            }
        }) {
            Text(text = "2")
        }
        Button(onClick = {
            scope.launch {
                currentFloor.value = 1
            }
        }) {
            Text(text = "1")
        }
    }

}

@Composable
fun Map1f(){
    val painter = painterResource(id = R.drawable.map1f)
    val size = painter.intrinsicSize
    Zoomable {
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier
                .aspectRatio(size.width / size.height)
                .fillMaxSize()
        )
    }

}

@Composable
fun Map2f(){
    val painter = painterResource(id = R.drawable.map2f)
    val size = painter.intrinsicSize
    Zoomable {
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier
                .aspectRatio(size.width / size.height)
                .fillMaxSize()
        )
    }
}

@Composable
fun Map3f(){
    val painter = painterResource(id = R.drawable.map3f)
    val size = painter.intrinsicSize
    Zoomable {
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier
                .aspectRatio(size.width / size.height)
                .fillMaxSize()
        )
    }
}

@Composable
fun Map4f() {
    val painter = painterResource(id = R.drawable.map4f)
    val size = painter.intrinsicSize
    Zoomable {
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier
                .aspectRatio(size.width / size.height)
                .fillMaxSize()
        )
    }
}

@Composable
fun Map5f(){
    val painter = painterResource(id = R.drawable.map5f)
    val size = painter.intrinsicSize
    Zoomable {
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier
                .aspectRatio(size.width / size.height)
                .fillMaxSize()
        )
    }
}
