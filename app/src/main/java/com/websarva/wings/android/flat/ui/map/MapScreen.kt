package com.websarva.wings.android.flat.ui.map

import com.websarva.wings.android.flat.R
import androidx.compose.foundation.Image
import android.view.Surface
import androidx.compose.foundation.layout.*
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mxalbert.zoomable.Zoomable
import com.websarva.wings.android.flat.ui.theme.FLATTheme

/*
    mapの画面を作成する関数
*/
@Composable
fun MapScreen (){
    FLATTheme {
        // この中にmapの画面を作成していく
//        val state = rememberZoomableState(
//            minScale = 2f
//        )
//        Zoomable(state = state) {
//            Text(text = "Zoom me!")
//        }

        Column(modifier = Modifier.fillMaxSize()){
            Zoomable {
                val painter = painterResource(id = R.drawable.map1f)
                val size = painter.intrinsicSize
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
}
