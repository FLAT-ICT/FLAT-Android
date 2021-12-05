package com.websarva.wings.android.flat.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.websarva.wings.android.flat.FLATApplication.Companion.myId
import com.websarva.wings.android.flat.R
import com.websarva.wings.android.flat.databinding.FragmentUserSettingBinding
import com.websarva.wings.android.flat.model.UserSettingItem
import com.websarva.wings.android.flat.viewmodel.UserSettingViewModel

class UserSettingFragment : Fragment() {
    private val viewModel: UserSettingViewModel by activityViewModels()
    private var _binding: FragmentUserSettingBinding? = null
    private val binding get() = _binding!!
    private lateinit var userSettingAdapter: UserSettingAdapter
    private lateinit var settingList: List<UserSettingItem>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getUserData()
        viewModel.user.observe(viewLifecycleOwner, {
            binding.apply {
                tvUserName.text = it.name
                //TODO: Status, iconのリソース設定
            }
        })

        settingList = listOf(
            UserSettingItem(0,getString(R.string.change_name)),
            UserSettingItem(1,getString(R.string.change_icon)),
            UserSettingItem(2, getString(R.string.change_status)),
            UserSettingItem(3, getString(R.string.logout))
        )
        binding.rvUserSettings.apply {
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager(context).orientation))
            layoutManager = LinearLayoutManager(context)
            adapter = UserSettingAdapter(viewLifecycleOwner, viewModel, UserSettingAdapter.OnClickListener { item -> viewModel.itemOnClick(item) }).also {
                userSettingAdapter = it
            }
        }
        userSettingAdapter.submitList(settingList)

        viewModel.logoutClicked.observe(viewLifecycleOwner, {
            LogoutDialogFragment(myId).show(childFragmentManager, "logoutDialog")
        })

        viewModel.nameChangeClicked.observe(viewLifecycleOwner, {
            val action = UserSettingFragmentDirections.actionUserSettingFragmentToNameChangeFragment()
            view.findNavController().navigate(action)
        })

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