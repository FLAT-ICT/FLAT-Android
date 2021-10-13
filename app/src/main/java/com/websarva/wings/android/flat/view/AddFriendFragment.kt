package com.websarva.wings.android.flat.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.websarva.wings.android.flat.R
import com.websarva.wings.android.flat.databinding.FragmentAddFriendBinding
import com.websarva.wings.android.flat.viewmodel.AddFriendViewModel

class AddFriendFragment : Fragment() {
    private val viewModel: AddFriendViewModel by viewModels()
    private var _binding: FragmentAddFriendBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddFriendBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // ツールバー左上のバツで友だち一覧画面に戻る
        binding.addFriendToolbar.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }

        binding.ibSearchId.setOnClickListener {
            val text = binding.etInputFriendId.text
            //TODO::テキストを全削除する処理
        }

        // キーボードの完了ボタンのリスナー
        binding.etInputFriendId.setOnEditorActionListener(editorAction)

        // getが成功した場合の処理
        viewModel.user.observe(viewLifecycleOwner, Observer {
//            Log.d("debug", "${viewModel.user.value}")
            binding.apply {
                tvNotFoundId.visibility = View.GONE
                tvAddFriendName.text = viewModel.user.value?.name
                tvAddFriendName.visibility = View.VISIBLE
                //TODO::ivSearchFriendにアイコンを設置する処理を書く
                //TODO::アイコンを設置したら下のINVISIBLEをVISIBLEにする
                cvSearchFriendPosition.visibility = View.INVISIBLE
                btApplyForFriend.visibility = View.VISIBLE
            }
        })

        // getが失敗した場合の処理
        viewModel.errorMessage.observe(viewLifecycleOwner, Observer {
            binding.apply {
                tvAddFriendName.visibility = View.GONE
                cvSearchFriendPosition.visibility = View.GONE
                btApplyForFriend.visibility = View.GONE
                tvNotFoundId.visibility = View.VISIBLE
            }
        })
    }

    // 物理キーボードのエンターやソフトウェアキーボードの完了を押したときの設定
    private val editorAction: TextView.OnEditorActionListener =
        TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH || event != null && event.keyCode == KeyEvent.KEYCODE_ENTER) {
                val id = binding.etInputFriendId.text.toString()
                binding.apply {
                    when {
                        etInputFriendId.text.isNullOrEmpty() -> {
                            tilInputFriendId.error = getString(R.string.empty_id)
                        }
                        etInputFriendId.text.toString().length < 6 -> {
                            tilInputFriendId.error = getString(R.string.short_id)
                        }
                        else -> {
                            viewModel.getUserInfo(id)
                        }
                    }
                }
                // キーボードを非表示にする
                val inputManager =
                    activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputManager.hideSoftInputFromWindow(
                    v.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS
                )
                true
            } else false
        }
}