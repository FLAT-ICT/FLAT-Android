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
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.websarva.wings.android.flat.R
import com.websarva.wings.android.flat.databinding.FragmentAddFriendBinding
import com.websarva.wings.android.flat.viewmodel.AddFriendViewModel

class AddFriendFragment : Fragment() {
    private val viewModel: AddFriendViewModel by viewModels()
    private var _binding: FragmentAddFriendBinding? = null
    private val binding get() = _binding!!
    private lateinit var addFriendAdapter: AddFriendAdapter

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

        binding.rvSearchedUsers.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = AddFriendAdapter(viewLifecycleOwner, viewModel).also {
                addFriendAdapter = it
            }
        }
        viewModel.apply {
            //GETが成功した場合にusersに変更が加わるので、GETの成功が保証されている
            users.observe(viewLifecycleOwner, {
                when {
                    it.isEmpty() -> {
                        binding.apply {
                            //rvをGONE(もしくはリストの初期化のみ)にすると次に通信に成功した場合に
                            //直前のリストの内容が一瞬だけ表示されるのでここはわざとINVISIBLEにしています
                            addFriendAdapter.submitList(null)
                            rvSearchedUsers.visibility = View.INVISIBLE
                            tvNotFoundId.visibility = View.VISIBLE
                        }
                    }
                    else -> {
                        addFriendAdapter.submitList(it)
                        binding.apply {
                            rvSearchedUsers.visibility = View.VISIBLE
                            tvNotFoundId.visibility = View.GONE
                        }
                    }
                }
            })
        }

        // キーボードの完了ボタンのリスナー
        binding.etInputFriendId.setOnEditorActionListener(editorAction)

        // 名前入力確定時の通信が成功したとき
        viewModel.getCode.observe(viewLifecycleOwner, {
            // GETが失敗したとき
            if (it != 200) {
                binding.apply {
                    //rvをGONE(もしくはリストの初期化のみ)にすると次に通信に成功した場合に
                    //直前のリストの内容が一瞬だけ表示されるのでここはわざとINVISIBLEにしています
                    addFriendAdapter.submitList(null)
                    rvSearchedUsers.visibility = View.INVISIBLE
                    tvNotFoundId.visibility = View.INVISIBLE
                    //TODO::エラー時の表示が定まり次第変えるかも
                    Toast.makeText(activity, R.string.connection_error.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        })

        // 友だち申請時の通信が成功したとき
        viewModel.postCode.observe(viewLifecycleOwner, {
            when (viewModel.postCode.value) {
                // getSearchUsersを呼んで情報を更新
                // POSTが成功したとき
                200 -> {
                    //TODO:ここでgetFriendsしちゃう？
                }
                // POSTが失敗したとき
                else -> {
                    //TODO::失敗したときのメッセージ等の表示
                }
            }
        })

        viewModel.errorMessage.observe(viewLifecycleOwner, {
            Log.d("error", "${viewModel.errorMessage.value}")
            //TODO::接続が確認されなかった等のメッセージの表示？
        })
    }

    var count = 0

    // 物理キーボードのエンターやソフトウェアキーボードの完了を押したときの設定
    //TODO::物理キーボードのエンターを押した後、フォーカスをエディットテキストにあてるとリスナーが反応するバグがある
    // 後で物理キーボードを無効にするか、修正するか諦めるか対応を考える
    private val editorAction: TextView.OnEditorActionListener =
        TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH || event != null && event.keyCode == KeyEvent.KEYCODE_ENTER) {
                binding.apply {
                    when {
                        etInputFriendId.text.isNullOrEmpty() -> {
                            tilInputFriendId.isErrorEnabled = true
                            tilInputFriendId.error = getString(R.string.empty_text)
                            addFriendAdapter.submitList(null)
                            rvSearchedUsers.visibility = View.INVISIBLE
                            tvNotFoundId.visibility = View.GONE
                        }
                        else -> {
                            tilInputFriendId.isErrorEnabled = false
                            val searchWord = binding.etInputFriendId.text.toString()
                            viewModel.searchUsers(searchWord)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}