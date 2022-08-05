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
import android.widget.Toast
import com.websarva.wings.android.flat.api.ResponseData
import java.io.BufferedReader
import java.io.InputStreamReader


data class Pin(val pin: ImageView, val name: String, val floor: Int)

class MapFragment : Fragment() {
    //    private val viewModel: MapViewModel by viewModels()
    private val viewModel: FriendListViewModel by viewModels()
    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!

    private val arrayOfPins = lazy {
        arrayOf(
            Pin(this.binding.ivPin0, "132教室", 1),
            Pin(this.binding.ivPin1, "364コンピュータ教室", 3),
            Pin(this.binding.ivPin2, "363コンピュータ教室", 3),
            Pin(this.binding.ivPin3, "工房", 3),
            Pin(this.binding.ivPin4, "体育館", 3),
            Pin(this.binding.ivPin5, "トレーニングルーム", 3),
            Pin(this.binding.ivPin6, "367大講義室", 3),
            Pin(this.binding.ivPin7, "368大講義室", 3),
            Pin(this.binding.ivPin8, "332教員室", 3),
            Pin(this.binding.ivPin9, "社会連携センタ", 3),
            Pin(this.binding.ivPin10, "ミュージアム", 3),
            Pin(this.binding.ivPin11, "モール", 3),
            Pin(this.binding.ivPin12, "3階エントランスホール", 3),
            Pin(this.binding.ivPin13, "127教員室", 1),
            Pin(this.binding.ivPin14, "ラウンジ1階西側", 1),
            Pin(this.binding.ivPin15, "124教員室", 1),
            Pin(this.binding.ivPin16, "プレゼンベイ／スタジオ1階", 1),
            Pin(this.binding.ivPin17, "食堂", 1),
            Pin(this.binding.ivPin18, "126教員室", 1),
            Pin(this.binding.ivPin19, "1階エントランスホール", 1),
            Pin(this.binding.ivPin20, "売店", 2),
            Pin(this.binding.ivPin21, "223教員室", 2),
            Pin(this.binding.ivPin22, "OSS研究室", 1)
        )
    }

    private lateinit var spots: List<String>


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


                spots = it.mutual.map { it.spot }

                updateMap()

                Log.d("friendSpot", "friendSpot = ${it.mutual.map { it.id }}")
            }
        }

        binding.btMap1.setOnClickListener { it ->
            binding.ivMap.setImageResource(R.drawable.map1f)
            floor = 1
            updateMap()
        }
        binding.btMap2.setOnClickListener {
            binding.ivMap.setImageResource(R.drawable.map2f)
            floor = 2
            updateMap()

        }
        binding.btMap3.setOnClickListener {
            binding.ivMap.setImageResource(R.drawable.map3f)
            floor = 3
            updateMap()
        }
        binding.btMap4.setOnClickListener {

            binding.ivMap.setImageResource(R.drawable.map4f)
            floor = 4
            updateMap()
        }
        binding.btMap5.setOnClickListener {
            binding.ivMap.setImageResource(R.drawable.map5f)
            floor = 5
            updateMap()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getFriends()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateMap() {
        arrayOfPins.value.forEach {
            Log.d("MapFragment", "$floor, $it")
            if (it.pin.visibility == View.VISIBLE) {
                it.pin.visibility = View.GONE
            }
        }

        arrayOfPins.value.forEach {
            if (it.name in spots && it.floor == floor) {
                it.pin.visibility = View.VISIBLE
            }
        }
    }

}

