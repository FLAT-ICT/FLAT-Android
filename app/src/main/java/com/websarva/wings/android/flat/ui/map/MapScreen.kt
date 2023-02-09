package com.websarva.wings.android.flat.ui.map

import android.util.Log
import com.websarva.wings.android.flat.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.mxalbert.zoomable.Zoomable
import com.websarva.wings.android.flat.ui.theme.FLATTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mxalbert.zoomable.rememberZoomableState
import com.websarva.wings.android.flat.viewmodel.FriendListViewModel

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
fun MapScreen (model: FriendListViewModel = viewModel()){

    val floor = remember { mutableStateOf(1) }

    val friends = model.friends.observeAsState()
    // これ正しく更新されなさそう
    val spots = friends.value?.mutual?.map { it.spot } ?: listOf()

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
            SchoolMap(floor.value, spots)
        }
    }
}

@Composable
fun ElevatorButton(currentFloor: MutableState<Int>, scope: CoroutineScope, parentWidth: Dp) {
    Column(
        // 右寄せ
        modifier = Modifier
            .zIndex(1f)
            // TODO:ハードコーディングがすぎるので直す
            .padding( parentWidth - 80.dp, 16.dp, 16.dp, 0.dp)
    ){
        (5 downTo 1).forEach {
            Button(onClick = {
                scope.launch {
                    currentFloor.value = it
                }
            }) {
                Text(text = it.toString())
            }
        }
    }

}

data class Floor(
    val floor: Int,
    val mapId: Int,
    val spots: List<Spot>
)

class FloorList {
    companion object {
        val floors = listOf(
            Floor(1, R.drawable.map1f, Pins.pins1f),
            Floor(2, R.drawable.map2f, Pins.pins2f),
            Floor(3, R.drawable.map3f, Pins.pins3f),
            Floor(4, R.drawable.map4f, Pins.pins4f),
            Floor(5, R.drawable.map5f, Pins.pins5f)
        )
    }
}


@Composable
fun SchoolMap(floor: Int, spots: List<String>) {

    val zoomableState = rememberZoomableState(
        minScale = 1f,
        maxScale = 3f
    )

    val mapId = FloorList.floors[floor - 1].mapId
//    val spots = FloorList.floors[floor - 1].spots.filter { spots.contains(it.name) }
    val spots1f = Pins.pins1f
    val painter = painterResource(id = mapId)

    val size = painter.intrinsicSize
    Log.d("MapScreen", "intrinsicWidth: ${size.width}, intrinsicHeight: ${size.height}")


    BoxWithConstraints {
        val boxWidth = with(LocalDensity.current) { constraints.maxWidth.toDp() }
        val boxHeight = with(LocalDensity.current) { constraints.maxHeight.toDp() }
        Log.d("MapScreen", "maxWidth: $maxWidth, maxHeight: $maxHeight")
        Zoomable(state = zoomableState) {

            // ピンの位置がずれるが，これはZoomableと地図の原点が異なることが原因
            // 要修正
            spots1f.map {
                Pin(it.x, it.y, size, boxWidth, boxHeight, zoomableState)
            }
            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier
                    .aspectRatio(size.width / size.height)
                    .fillMaxSize()
            )
        }
    }
}
