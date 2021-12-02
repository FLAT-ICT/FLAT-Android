package com.websarva.wings.android.flat.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.websarva.wings.android.flat.R
import com.websarva.wings.android.flat.databinding.FragmentUserSettingBinding
import com.websarva.wings.android.flat.viewmodel.UserSettingViewModel

class UserSettingFragment : Fragment() {
    private val viewModel: UserSettingViewModel by activityViewModels()
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
            val action = UserSettingFragmentDirections.actionUserSettingFragmentToStartupFragment()
            view.findNavController().navigate(action)
        })

        viewModel.error.observe(viewLifecycleOwner, {
            Toast.makeText(activity, getString(R.string.connection_error), Toast.LENGTH_SHORT).show()
        })
    }

}