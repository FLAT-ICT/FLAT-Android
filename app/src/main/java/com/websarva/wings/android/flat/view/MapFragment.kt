package com.websarva.wings.android.flat.view

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
//import com.websarva.wings.android.flat.viewmodel.MapViewModel
import com.websarva.wings.android.flat.viewmodel.FriendListViewModel
import org.json.JSONException
import org.json.JSONObject
import org.w3c.dom.Node
import android.view.ViewGroup.LayoutParams
import android.widget.ImageView
import java.io.BufferedReader
import java.io.InputStreamReader


data class Pin (val pin: ImageView, val name: String)

class MapFragment : Fragment() {
    //    private val viewModel: MapViewModel by viewModels()
    private val viewModel: FriendListViewModel by viewModels()
    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!

    private val arrayOfPins = arrayOf(
        Pin(this.binding.ivPin0, "132教室"),
        Pin(this.binding.ivPin1, "364コンピュータ教室"),
        Pin(this.binding.ivPin2, "363コンピュータ教室"),
        Pin(this.binding.ivPin3, "工房"),
        Pin(this.binding.ivPin4, "体育館"),
        Pin(this.binding.ivPin5, "トレーニングルーム"),
        Pin(this.binding.ivPin6, "367大講義室"),
        Pin(this.binding.ivPin7, "368大講義室"),
        Pin(this.binding.ivPin8, "332教員室"),
        Pin(this.binding.ivPin9, "社会連携センタ"),
        Pin(this.binding.ivPin10, "ミュージアム"),
        Pin(this.binding.ivPin11, "モール"),
        Pin(this.binding.ivPin12, "3階エントランスホール"),
        Pin(this.binding.ivPin13, "127教員室"),
        Pin(this.binding.ivPin14, "ラウンジ1階西側"),
        Pin(this.binding.ivPin15, "124教員室"),
        Pin(this.binding.ivPin16, "プレゼンベイ／スタジオ1階"),
        Pin(this.binding.ivPin17, "食堂"),
        Pin(this.binding.ivPin18, "126教員室"),
        Pin(this.binding.ivPin19, "1階エントランスホール"),
        Pin(this.binding.ivPin20, "売店"),
        Pin(this.binding.ivPin21, "223教員室"),
        Pin(this.binding.ivPin22, "OSS研究室")
        )

    private var floor = 1 // TODO: 現在の階層を保存．これだとMap呼ぶたびにリセットされてしまうので，上で管理する

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.friends.observe(viewLifecycleOwner) {
            binding.apply {


                val spots = it.mutual.map { it.spot }

                arrayOfPins.forEach {
                    if (it.pin.visibility == View.VISIBLE){
                        it.pin.visibility = View.GONE
                    }
                }

                arrayOfPins.forEach {
                    if (it.name in spots) {
                        it.pin.visibility = View.VISIBLE
                    }
                }

                Log.d("friendSpot", "friendSpot = ${it.mutual.map { it.id }}")
//                if (it.mutual.map { it.spot[0] }.toString() == "132教員室") {
//                    ivPin0.visibility = View.VISIBLE
//                } else {
//                    ivPin0.visibility = View.INVISIBLE
//                }
            }
            Log.d("check", "L99 apply complete")
        }

        Log.d("check", "L101")
        binding.btMap1.setOnClickListener {
            binding.ivMap.setImageResource(R.drawable.map1f)
            floor = 1
        }
        binding.btMap2.setOnClickListener {
            binding.ivMap.setImageResource(R.drawable.map2f)
            floor = 2
        }
        binding.btMap3.setOnClickListener {
            binding.ivMap.setImageResource(R.drawable.map3f)
            floor = 3
        }
        binding.btMap4.setOnClickListener {
            binding.ivMap.setImageResource(R.drawable.map4f)
            floor = 4
        }
        binding.btMap5.setOnClickListener {
            binding.ivMap.setImageResource(R.drawable.map5f)
            floor = 5
        }
        Log.d("check", "L122")
    }

    override fun onResume() {
        super.onResume()
        viewModel.getFriends()
    }
}

