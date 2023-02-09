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


    val dpx = x * 2.75 * widthRatio * scale
    val dpy = y * 2.75 * widthRatio * scale
    Log.d("MapScreen", "dpx: $dpx, dpy: $dpy")
    Image(painter = painterResource(id = R.drawable.pin), contentDescription = null, modifier = Modifier
        .offset(x = dpx.dp, y = dpy.dp)
        .zIndex(1f)
        .size(20.dp)
    )
}

data class Spot(val name: String, val x: Float, val y: Float)

class Pins {
    companion object {
        val pins1f = listOf(
            Spot("127教室", 56.615154f, 50.29734f),
        )
        val pins2f = listOf(
            Spot("127教室", 0.5f, 0.5f),
        )
        val pins3f = listOf(
            Spot("127教室", 0.5f, 0.5f),
        )
        val pins4f = listOf(
            Spot("127教室", 0.5f, 0.5f),
        )
        val pins5f = listOf(
            Spot("127教室", 0.5f, 0.5f),
        )
    }
}
