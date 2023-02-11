package com.websarva.wings.android.flat.ui.map

import android.content.Context
import android.util.DisplayMetrics
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.mxalbert.zoomable.ZoomableState
import com.websarva.wings.android.flat.R
import com.websarva.wings.android.flat.model.User

fun convertDp2Px(dp: Float, context: Context): Float {
    val metrics: DisplayMetrics = context.resources.displayMetrics
    return dp * metrics.density
}

fun convertPx2Dp(px: Float, context: Context): Float {
    val metrics = context.resources.displayMetrics
    return px / metrics.density
}


@Composable
fun Pin(
    x: Float, y: Float, imageSize: Size,
    parentWidth: Dp, parentHeight: Dp,
    zoomableState: ZoomableState,
    user: User? = null){
    // 大きさ固定
    // 地図の大きさは，100px x 100px
    // ピンの座標はこれに準じて決められている．
    // 拡大率は，Android上での地図の大きさが275dp x 275dp だから
    // 画面の横幅dp / 275dp とする．
    // 画面の横幅をmaxWidthとして，
    // pin_x <- pin_x * (275/100) * (maxWidth / 275)
    //                  ^^^^^^^^^ px -> dp の倍率
    //                              ^^^^^^^^^^^^^^^^ 画面の横幅に対する地図の横幅の拡大率

    val imageWidth = imageSize.width.dp
    val imageHeight = imageSize.height.dp
    val widthRatio = parentWidth / imageWidth
    val heightRatio = parentHeight / imageHeight
    val scale = zoomableState.scale
    Log.d("MapScreen", "parentWidth: $parentWidth, parentHeight: $parentHeight")
    Log.d("MapScreen", "imageWidth: $imageWidth, imageHeight: $imageHeight")
    Log.d("MapScreen", "widthRatio: $widthRatio, heightRatio: $heightRatio")
    Log.d("MapScreen", "x: $x, y: $y")
//    val parentWidth = parentSize


    val dpx = (x * imageSize.width  / 100 ) * widthRatio * scale
    val dpy = (y * imageSize.height / 100 ) * widthRatio  * scale
    Log.d("MapScreen", "dpx: $dpx, dpy: $dpy")
    val pinImage = painterResource(id = R.drawable.pin)
    Image(painter = pinImage, contentDescription = null, modifier = Modifier
        .offset(x = dpx.dp - 20.dp, y = dpy.dp - 40.dp)
        .zIndex(1f)
        .size(40.dp)
    )
}

data class Spot(val name: String, val x: Float, val y: Float)

class Pins {
    companion object {
        val pins1f = listOf(
            Spot("127教員室", 56.615154f, 50.29734f),
            Spot("OSS研究室", 55.69807f, 54.1509f),
            Spot("132教員室", 32.28002f, 50.242218f),
            Spot("食堂", 86.81355f, 63.27387f),
            Spot("126教員室", 59.632225f, 50.35584f),
            Spot("1階エントランスホール", 77.584435f, 63.354095f),
            Spot("ラウンジ1階西側", 37.968803f, 60.85418f),
            Spot("124教員室", 65.64274f, 50.321342f),
            Spot("プレゼンベイ／スタジオ1階", 59.48086f, 66.10382f),
        )
        val pins2f = listOf(
            Spot("223教員室", 69.073166f, 41.011322f),
            Spot("大場研究室／スタジオ2階東側", 69.09968f, 49.163227f),
            Spot("243/244実験室", 21.07838f, 36.67778f),
            Spot("スタジオ2階東側", 58.127827f, 49.124043f),
            Spot("売店", 88.8614f, 50.147995f),
            Spot("スタジオ2階東側", 49.37668f, 49.143448f),
            Spot("岡本研究室／スタジオ2階西側", 23.319387f, 48.489517f),
        )
        val pins3f = listOf(
            Spot("332教員室", 27.820704f, 58.21668f),
            Spot("365コンピュータ教室", 44.50808f, 46.91493f),
            Spot("工房", 44.526447f, 22.324224f),
            Spot("363コンピュータ教室", 61.44228f, 46.887077f),
            Spot("トレーニングルーム", 35.85082f, 13.510811f),
            Spot("社会連携センタ", 61.637245f, 28.613016f),
            Spot("モール", 32.253963f, 36.7642f),
            Spot("モール", 63.546604f, 36.802753f),
            Spot("3階エントランスホール", 77.00479f, 36.835857f),
            Spot("367大講義室", 18.694263f, 45.816936f),
            Spot("368大講義室", 28.062334f, 45.819294f),
            Spot("ミュージアム", 78.6088f, 27.418053f),
            Spot("体育館", 23.337206f, 20.256569f),
            Spot("364コンピュータ教室", 53.09414f, 46.940758f)
        )
        val pins4f = listOf(
            Spot("127教室", 0.5f, 0.5f),
        )
        val pins5f = listOf(
            Spot("127教室", 0.5f, 0.5f),
        )
        val akiba = listOf(
            Spot("エントランスエリア", 25.66f, 71.18f),
            Spot("休憩ブース", 71.43f, 49.94f),
            Spot("交流エリア", 16.83f, 44.63f),
            Spot("函館補完計画ブース", 48.87f, 76.09f),
            Spot("高度ICT演習ブース", 45.34f, 34.67f),
        )
    }
}
