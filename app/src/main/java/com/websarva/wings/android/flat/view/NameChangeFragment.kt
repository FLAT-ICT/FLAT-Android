package com.websarva.wings.android.flat.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.websarva.wings.android.flat.R
import com.websarva.wings.android.flat.databinding.FragmentNameChangeBinding
import com.websarva.wings.android.flat.viewmodel.NameChangeViewModel
import com.websarva.wings.android.flat.viewmodel.UserSettingViewModel


class NameChangeFragment : Fragment() {
    private val viewModel: NameChangeViewModel by viewModels()
    private var _binding: FragmentNameChangeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNameChangeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            btNewNameDecision.setOnClickListener {
                viewModel.trimName(etInputNewName.text.toString())
            }
        }

        viewModel.trimmedName.observe(viewLifecycleOwner, {
            when (it) {
                "" -> {
                    binding.tilInputNewName.apply {
                        error = getString(R.string.input_nickname_error)
                        isErrorEnabled = true
                    }
                }
                else -> {
                    binding.tilInputNewName.apply {
                        isErrorEnabled = false
                    }
                    viewModel.postUpdateName(it)
                }
            }
        })

        viewModel.errorMessage.observe(viewLifecycleOwner, {
            Toast.makeText(activity, getString(R.string.connection_error), Toast.LENGTH_SHORT).show()
        })

        viewModel.postResponse.observe(viewLifecycleOwner, {
            when (it.code()) {
                200 -> {
                    binding.tvNameChangeError.apply {
                        visibility = View.GONE
                    }
                    viewModel.updateRoom(it.body()!!)
                }
                400 -> {
                    binding.tvNameChangeError.apply {
                        text = getString(R.string.already_used_nickname_error)
                        visibility = View.VISIBLE
                    }
                }
                404 -> {
                    //TODO: Idが無い場合(?)
                }
                422 -> {
                    binding.tvNameChangeError.apply {
                        text = getString(R.string.validation_error)
                        visibility = View.VISIBLE
                    }
                }
            }
        })

        viewModel.isUpdated.observe(viewLifecycleOwner, {
            val action = NameChangeFragmentDirections.actionNameChangeFragmentToUserSettingFragment()
            view.findNavController().navigate(action)
        })
    }
}