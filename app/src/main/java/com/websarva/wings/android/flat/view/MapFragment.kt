package com.websarva.wings.android.flat.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import com.google.android.material.composethemeadapter.MdcTheme
import com.mxalbert.zoomable.Zoomable
import com.websarva.wings.android.flat.R
import com.websarva.wings.android.flat.ui.MapPOCTheme

class MapFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        return ComposeView(requireContext()).apply {
            setContent {
                MapPOCTheme {
                    // A surface container using the 'background' color from the theme
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colors.background
                    ) {
                        Map()
                    }
                }
                TestButton()
            }
        }
    }
}

//@Composable
//fun Map() {
//    Zoomable {
//        val painter = painterResource(R.drawable.map)
//        val size = painter.intrinsicSize
//        Image(
//            painter = painter,
//            contentDescription = "Map",
//            contentScale = ContentScale.Fit,
//            modifier = Modifier
//                .aspectRatio(size.width / size.height)
//        )
//        Image(
//            painter = painterResource(R.drawable.pin),
//            contentDescription = "Pin",
//            modifier = Modifier.absoluteOffset(50.dp, 200.dp)
//        )
//    }
//}

@Composable
fun Map() {
//    Zoomable {
//        val painter = painterResource(R.drawable.map)
//        val size = painter.intrinsicSize
//        var now_size = 1
//        Image(
//            painter = painter,
//            contentDescription = "Map",
//            contentScale = ContentScale.Fit,
//            modifier = Modifier.aspectRatio(size.width/size.height)
//                .onSizeChanged{
//                    Log.d("size", it.width.toString())
//                    now_size = it.width
//                    Log.d("size_scope", now_size.toString())
//                    Log.d("zahyou", (50*(now_size/size.width)).dp.toString())
//                }
//        )
//        Image(
//            painter = painterResource(R.drawable.pin),
//            contentDescription = "Pin",
//            modifier = Modifier.absoluteOffset((50*now_size/size.width).dp, 200.dp)
//        )
//    }

//    Zoomable {
//        val painter = painterResource(R.drawable.map)
//        val size = painter.intrinsicSize
//        var now_size = 1
//        Box (modifier = Modifier.aspectRatio(size.width / size.height)){
//            Image(
//                painter = painter,
//                contentDescription = "Map",
//                contentScale = ContentScale.Fit,
//                modifier = Modifier.fillMaxWidth()
//            )
//            Image(
//                painter = painterResource(R.drawable.pin),
//                contentDescription = "Pin",
//                modifier = Modifier.absoluteOffset(50.dp, 200.dp)
//            )
//        }
//    }


    val painter = painterResource(R.drawable.map)

    Image(
        painter = painter,
        contentDescription = "Map",
        contentScale = ContentScale.Fit,
    )
    Image(
        painter = painterResource(R.drawable.pin),
        contentDescription = "Pin",
        modifier = Modifier
            .absoluteOffset(50.dp, 200.dp)
            .scale(0.2f)
    )
}


@Composable
private fun TestButton() {
    Button(
        onClick = { Log.d("test", "クリックされた！！！") },
        modifier = Modifier.width(120.dp)
            .height(48.dp)
    ) {
        Text(text = "クリック")
    }
}