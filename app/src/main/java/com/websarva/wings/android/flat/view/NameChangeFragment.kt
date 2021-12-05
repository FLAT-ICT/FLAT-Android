package com.websarva.wings.android.flat.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
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
                    //TODO: post update name
                }
            }
        })
    }
}