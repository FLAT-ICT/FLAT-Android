package com.websarva.wings.android.flat.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.websarva.wings.android.flat.FLATApplication.Companion.myId
import com.websarva.wings.android.flat.R
import com.websarva.wings.android.flat.databinding.FragmentLoginBinding
import com.websarva.wings.android.flat.databinding.FragmentUserSettingBinding
import com.websarva.wings.android.flat.viewmodel.LoginViewModel
import com.websarva.wings.android.flat.viewmodel.UserSettingViewModel

class UserSettingFragment : Fragment() {
    private val viewModel: UserSettingViewModel by viewModels()
    private var _binding: FragmentUserSettingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            btLogout.setOnSafeClickListener {
                viewModel.logout(myId)
            }
        }

        viewModel.logoutResponse.observe(viewLifecycleOwner, {
            when (it.code()) {
                200 -> {
                    viewModel.deleteUserData()
                }
                else -> {
                    //TODO: Logout失敗
                }
            }
        })

        viewModel.roomChanged.observe(viewLifecycleOwner, {
            //TODO: 画面遷移
        })

        viewModel.error.observe(viewLifecycleOwner, {
            Toast.makeText(activity, getString(R.string.connection_error), Toast.LENGTH_SHORT).show()
        })
    }

}