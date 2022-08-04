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
import java.io.BufferedReader
import java.io.InputStreamReader

class MapFragment : Fragment() {
//    private val viewModel: MapViewModel by viewModels()
    private val viewModel: FriendListViewModel by viewModels()
    private var _binding: FragmentMapBinding? = null
    private val binding get()= _binding!!

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
                Log.d("friendSpot", "friendSpot = ${it.mutual.map { it.spot }}")
                if (it.mutual.map { it.spot }.toString() == "132教員室") {
                    ivPin0.visibility = View.VISIBLE
                } else {
                    ivPin0.visibility = View.INVISIBLE
                }
                if (it.mutual.map { it.spot }.toString() == "364コンピュータ教室") {
                    ivPin1.visibility = View.VISIBLE
                } else {
                    ivPin1.visibility = View.INVISIBLE
                }
                if (it.mutual.map { it.spot }.toString() == "363コンピュータ教室") {
                    ivPin2.visibility = View.VISIBLE
                } else {
                    ivPin2.visibility = View.INVISIBLE
                }
                if (it.mutual.map { it.spot }.toString() == "工房") {
                    ivPin3.visibility = View.VISIBLE
                } else {
                    ivPin3.visibility = View.INVISIBLE
                }
                if (it.mutual.map { it.spot }.toString() == "体育館") {
                    ivPin4.visibility = View.VISIBLE
                } else {
                    ivPin4.visibility = View.INVISIBLE
                }
                if (it.mutual.map { it.spot }.toString() == "トレーニングルーム") {
                    ivPin5.visibility = View.VISIBLE
                } else {
                    ivPin5.visibility = View.INVISIBLE
                }
                if (it.mutual.map { it.spot }.toString() == "367大講義室") {
                    ivPin6.visibility = View.VISIBLE
                } else {
                    ivPin6.visibility = View.INVISIBLE
                }
                if (it.mutual.map { it.spot }.toString() == "368大講義室") {
                    ivPin7.visibility = View.VISIBLE
                } else {
                    ivPin7.visibility = View.INVISIBLE
                }
                if (it.mutual.map { it.spot }.toString() == "332教員室") {
                    ivPin8.visibility = View.VISIBLE
                } else {
                    ivPin8.visibility = View.INVISIBLE
                }
                if (it.mutual.map { it.spot }.toString() == "社会連携センタ") {
                    ivPin9.visibility = View.VISIBLE
                } else {
                    ivPin9.visibility = View.INVISIBLE
                }
                if (it.mutual.map { it.spot }.toString() == "ミュージアム") {
                    ivPin10.visibility = View.VISIBLE
                } else {
                    ivPin10.visibility = View.INVISIBLE
                }
                if (it.mutual.map { it.spot }.toString() == "モール") {
                    ivPin11.visibility = View.VISIBLE
                } else {
                    ivPin11.visibility = View.INVISIBLE
                }
                if (it.mutual.map { it.spot }.toString() == "3階エントランスホール") {
                    ivPin12.visibility = View.VISIBLE
                } else {
                    ivPin12.visibility = View.INVISIBLE
                }
                if (it.mutual.map { it.spot }.toString() == "127教員室") {
                    ivPin13.visibility = View.VISIBLE
                } else {
                    ivPin13.visibility = View.INVISIBLE
                }
                if (it.mutual.map { it.spot }.toString() == "ラウンジ1階西側") {
                    ivPin14.visibility = View.VISIBLE
                } else {
                    ivPin14.visibility = View.INVISIBLE
                }
                if (it.mutual.map { it.spot }.toString() == "124教員室") {
                    ivPin15.visibility = View.VISIBLE
                } else {
                    ivPin15.visibility = View.INVISIBLE
                }
                if (it.mutual.map { it.spot }.toString() == "プレゼンベイ／スタジオ1階") {
                    ivPin16.visibility = View.VISIBLE
                } else {
                    ivPin16.visibility = View.INVISIBLE
                }
                if (it.mutual.map { it.spot }.toString() == "食堂") {
                    ivPin17.visibility = View.VISIBLE
                } else {
                    ivPin17.visibility = View.INVISIBLE
                }
                if (it.mutual.map { it.spot }.toString() == "126教員室") {
                    ivPin18.visibility = View.VISIBLE
                } else {
                    ivPin18.visibility = View.INVISIBLE
                }
                if (it.mutual.map { it.spot }.toString() == "1階エントランスホール") {
                    ivPin19.visibility = View.VISIBLE
                } else {
                    ivPin19.visibility = View.INVISIBLE
                }
                if (it.mutual.map { it.spot }.toString() == "売店") {
                    ivPin20.visibility = View.VISIBLE
                } else {
                    ivPin20.visibility = View.INVISIBLE
                }
                if (it.mutual.map { it.spot }.toString() == "223教員室") {
                    ivPin21.visibility = View.VISIBLE
                } else {
                    ivPin21.visibility = View.INVISIBLE
                }

            }
        }
//        viewModel.getUserData()
//        viewModel.user.observe(viewLifecycleOwner) {
//            binding.apply {
//                Log.d("spot", "spot = $it")
//                if (it.spot == "132教員室") {
//                    ivPin0.visibility = View.VISIBLE
//                } else {
//                    ivPin0.visibility = View.INVISIBLE
//                }
//                if (it.spot == "364コンピュータ教室") {
//                    ivPin1.visibility = View.VISIBLE
//                } else {
//                    ivPin1.visibility = View.INVISIBLE
//                }
//                if (it.spot == "363コンピュータ教室") {
//                    ivPin2.visibility = View.VISIBLE
//                } else {
//                    ivPin2.visibility = View.INVISIBLE
//                }
//                if (it.spot == "工房") {
//                    ivPin3.visibility = View.VISIBLE
//                } else {
//                    ivPin3.visibility = View.INVISIBLE
//                }
//                if (it.spot == "体育館") {
//                    ivPin4.visibility = View.VISIBLE
//                } else {
//                    ivPin4.visibility = View.INVISIBLE
//                }
//                if (it.spot == "トレーニングルーム") {
//                    ivPin5.visibility = View.VISIBLE
//                } else {
//                    ivPin5.visibility = View.INVISIBLE
//                }
//                if (it.spot == "367大講義室") {
//                    ivPin6.visibility = View.VISIBLE
//                } else {
//                    ivPin6.visibility = View.INVISIBLE
//                }
//                if (it.spot == "368大講義室") {
//                    ivPin7.visibility = View.VISIBLE
//                } else {
//                    ivPin7.visibility = View.INVISIBLE
//                }
//                if (it.spot == "332教員室") {
//                    ivPin8.visibility = View.VISIBLE
//                } else {
//                    ivPin8.visibility = View.INVISIBLE
//                }
//                if (it.spot == "社会連携センタ") {
//                    ivPin9.visibility = View.VISIBLE
//                } else {
//                    ivPin9.visibility = View.INVISIBLE
//                }
//                if (it.spot == "ミュージアム") {
//                    ivPin10.visibility = View.VISIBLE
//                } else {
//                    ivPin10.visibility = View.INVISIBLE
//                }
//                if (it.spot == "モール") {
//                    ivPin11.visibility = View.VISIBLE
//                } else {
//                    ivPin11.visibility = View.INVISIBLE
//                }
//                if (it.spot == "3階エントランスホール") {
//                    ivPin12.visibility = View.VISIBLE
//                } else {
//                    ivPin12.visibility = View.INVISIBLE
//                }
//                if (it.spot == "127教員室") {
//                    ivPin13.visibility = View.VISIBLE
//                } else {
//                    ivPin13.visibility = View.INVISIBLE
//                }
//                if (it.spot == "ラウンジ1階西側") {
//                    ivPin14.visibility = View.VISIBLE
//                } else {
//                    ivPin14.visibility = View.INVISIBLE
//                }
//                if (it.spot == "124教員室") {
//                    ivPin15.visibility = View.VISIBLE
//                } else {
//                    ivPin15.visibility = View.INVISIBLE
//                }
//                if (it.spot == "プレゼンベイ／スタジオ1階") {
//                    ivPin16.visibility = View.VISIBLE
//                } else {
//                    ivPin16.visibility = View.INVISIBLE
//                }
//                if (it.spot == "食堂") {
//                    ivPin17.visibility = View.VISIBLE
//                } else {
//                    ivPin17.visibility = View.INVISIBLE
//                }
//                if (it.spot == "126教員室") {
//                    ivPin18.visibility = View.VISIBLE
//                } else {
//                    ivPin18.visibility = View.INVISIBLE
//                }
//                if (it.spot == "1階エントランスホール") {
//                    ivPin19.visibility = View.VISIBLE
//                } else {
//                    ivPin19.visibility = View.INVISIBLE
//                }
//                if (it.spot == "売店") {
//                    ivPin20.visibility = View.VISIBLE
//                } else {
//                    ivPin20.visibility = View.INVISIBLE
//                }
//                if (it.spot == "223教員室") {
//                    ivPin21.visibility = View.VISIBLE
//                } else {
//                    ivPin21.visibility = View.INVISIBLE
//                }
//
//            }
//        }

        binding.btMap1.setOnClickListener{
            binding.ivMap.setImageResource(R.drawable.map1f)
        }
        binding.btMap2.setOnClickListener{
            binding.ivMap.setImageResource(R.drawable.map2f)
        }
        binding.btMap3.setOnClickListener{
            binding.ivMap.setImageResource(R.drawable.map3f)
        }
        binding.btMap4.setOnClickListener{
            binding.ivMap.setImageResource(R.drawable.map4f)
        }
        binding.btMap5.setOnClickListener{
            binding.ivMap.setImageResource(R.drawable.map5f)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getFriends()
    }
}
