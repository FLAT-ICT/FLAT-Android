package com.websarva.wings.android.flat.ui.map

import android.app.ActionBar
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.marginBottom
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.websarva.wings.android.flat.R
import com.websarva.wings.android.flat.databinding.FragmentMapBinding
//import com.websarva.wings.android.flat.ui.map.MapViewModel
import com.websarva.wings.android.flat.viewmodel.FriendListViewModel
import org.json.JSONException
import org.json.JSONObject
import org.w3c.dom.Node
import android.view.ViewGroup.LayoutParams
import android.widget.ImageView
import android.widget.Toast
import androidx.compose.ui.platform.ComposeView
import androidx.navigation.findNavController
import com.websarva.wings.android.flat.api.ResponseData
import java.io.BufferedReader
import java.io.InputStreamReader


//data class Pin(val pin: ImageView, val name: String, val floor: Int)

class MapFragment : Fragment() {
    //    private val viewModel: MapViewModel by viewModels()
    private val viewModel: FriendListViewModel by viewModels()
    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!
//
//    private val arrayOfPins: Array<Pin> by lazy{ arrayOf(
//        Pin(this.binding.ivPin1, "127教員室", 1),
//        Pin(this.binding.ivPin22, "132教員室", 1),
//        Pin(this.binding.ivPin69, "ラウンジ1階西側", 1),
//        Pin(this.binding.ivPin101, "124教員室", 1),
//        Pin(this.binding.ivPin169, "プレゼンベイ／スタジオ1階", 1),
//        Pin(this.binding.ivPin42, "食堂", 1),
//        Pin(this.binding.ivPin61, "126教員室", 1),
//        Pin(this.binding.ivPin62, "1階エントランスホール", 1),
//        Pin(this.binding.ivPin21, "OSS研究室", 1),
//        Pin(this.binding.ivPin104, "売店", 2),
//        Pin(this.binding.ivPin4, "223教員室", 2),
//        Pin(this.binding.ivPin24, "大場研究室／スタジオ2階東側", 2),
//        Pin(this.binding.ivPin105, "スタジオ2階東側", 2),
//        Pin(this.binding.ivPin48, "スタジオ2階東側", 2),
//        Pin(this.binding.ivPin150, "岡本研究室／スタジオ2階西側", 2),
//        Pin(this.binding.ivPin44, "243/244実験室", 2),
//        Pin(this.binding.ivPin26, "365コンピュータ教室", 3),
//        Pin(this.binding.ivPin183, "364コンピュータ教室", 3),
//        Pin(this.binding.ivPin46, "363コンピュータ教室", 3),
//        Pin(this.binding.ivPin43, "工房", 3),
//        Pin(this.binding.ivPin181, "体育館", 3),
//        Pin(this.binding.ivPin82, "トレーニングルーム", 3),
//        Pin(this.binding.ivPin141, "367大講義室", 3),
//        Pin(this.binding.ivPin142, "368大講義室", 3),
//        Pin(this.binding.ivPin5, "332教員室", 3),
//        Pin(this.binding.ivPin89, "社会連携センタ", 3),
//        Pin(this.binding.ivPin149, "ミュージアム", 3),
//        Pin(this.binding.ivPin102, "モール", 3),
//        Pin(this.binding.ivPin121, "3階エントランスホール", 3),
//        Pin(this.binding.ivPin103, "モール", 3)
//    )}

    private lateinit var spots: List<String>


    private var floor = 1 // TODO: 現在の階層を保存．これだとMap呼ぶたびにリセットされてしまうので，上で管理する

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(inflater.context).apply {
        setContent {
            MapScreen(
//                onNavigate = {dest -> findNavController().navigate(dest)},
            )
        }
    }

//    private fun updateMap() {
//        arrayOfPins.forEach {
//            Log.d("MapFragment", "$floor, $it")
//            if (it.pin.visibility == View.VISIBLE) {
//                it.pin.visibility = View.GONE
//            }
//        }
//
//        arrayOfPins.forEach {
//            if (it.name in spots && it.floor == floor) {
//                it.pin.visibility = View.VISIBLE
//            }
//        }
//    }
}

