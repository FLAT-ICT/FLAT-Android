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
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
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

        binding.ibSearchId.setOnClickListener {
            val text = binding.etInputFriendId.text
            //TODO::テキストを全削除する処理
        }

        // キーボードの完了ボタンのリスナー
        binding.etInputFriendId.setOnEditorActionListener(editorAction)

        // ID入力確定時の通信が成功したとき
        viewModel.getCode.observe(viewLifecycleOwner, Observer {
            when (viewModel.getCode.value) {
                // GETが成功したとき
                200 -> {
                    viewModel.user.observe(viewLifecycleOwner, Observer {
                        binding.apply {
                            tvNotFoundId.visibility = View.GONE
                            tvAddFriendName.apply {
                                text = viewModel.user.value?.name
                                visibility = View.VISIBLE
                            }
                            //TODO::ivSearchFriendにアイコンを設置する処理を書く
                            //TODO::アイコンを設置したら下のINVISIBLEをVISIBLEにする
                            cvSearchFriendPosition.visibility = View.INVISIBLE
                            btApplyForFriend.apply {
                                //TODO::承認待ちならばisClickableをfalseにして、テキストや色を入れ替え
                                text = getString(R.string.apply_for_friend)
                                setTextColor(ContextCompat.getColor(context, R.color.white))
                                setBackgroundColor(
                                    ContextCompat.getColor(
                                        context,
                                        R.color.primary_solid
                                    )
                                )
                                isClickable = true
                                visibility = View.VISIBLE
                            }
                        }
                    })
                }
                // GETが失敗したとき
                else -> {
                    binding.apply {
                        tvAddFriendName.visibility = View.GONE
                        cvSearchFriendPosition.visibility = View.GONE
                        btApplyForFriend.visibility = View.GONE
                        tvNotFoundId.visibility = View.VISIBLE
                    }
                }
            }
        })

        // 申請ボタンを押したとき
        binding.btApplyForFriend.apply {
            setOnClickListener {
                isClickable = false
                viewModel.postFriendRequest(viewModel.user.value!!.id)
            }
        }

        // 友だち申請時の通信が成功したとき
        viewModel.postCode.observe(viewLifecycleOwner, Observer {
            when (viewModel.postCode.value) {
                // POSTが成功したとき
                200 -> {
                    binding.btApplyForFriend.apply {
                        text = getString(R.string.wait_for_approval)
                        setTextColor(ContextCompat.getColor(context, R.color.middle))
                        setBackgroundColor(ContextCompat.getColor(context, R.color.primary_pale))
                    }
                }
                // POSTが失敗したとき
                else -> {
                    //TODO::失敗したときのメッセージ等の表示
                }
            }
        })

        viewModel.errorMessage.observe(viewLifecycleOwner, Observer {
            Log.d("error", "${viewModel.errorMessage.value}")
            //TODO::接続が確認されなかった等のメッセージの表示？
        })
    }

    // 物理キーボードのエンターやソフトウェアキーボードの完了を押したときの設定
    //TODO::物理キーボードのエンターを押した後、フォーカスをエディットテキストにあてるとリスナーが反応するバグがある
    // 後で物理キーボードを無効にするか、修正するか諦めるか対応を考える
    private val editorAction: TextView.OnEditorActionListener =
        TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH || event != null && event.keyCode == KeyEvent.KEYCODE_ENTER) {
                val id = binding.etInputFriendId.text.toString()
                binding.apply {
                    when {
                        etInputFriendId.text.isNullOrEmpty() -> {
                            tilInputFriendId.isErrorEnabled = true
                            tilInputFriendId.error = getString(R.string.empty_id)
                        }
                        etInputFriendId.text.toString().length < 6 -> {
                            tilInputFriendId.isErrorEnabled = true
                            tilInputFriendId.error = getString(R.string.short_id)
                        }
                        else -> {
                            tilInputFriendId.isErrorEnabled = false
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