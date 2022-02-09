package com.websarva.wings.android.flat.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.websarva.wings.android.flat.R
import com.websarva.wings.android.flat.databinding.FragmentStatusChangeBinding
import com.websarva.wings.android.flat.viewmodel.StatusChangeViewModel

class StatusChangeFragment : BottomSheetDialogFragment() {
    private val viewModel: StatusChangeViewModel by viewModels()
    private var _binding: FragmentStatusChangeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStatusChangeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.layoutAtSchool.apply {
            ivSelectStatus.setImageResource(R.drawable.ic_at_school)
            tvSelectStatus.text = getString(R.string.at_school)
            layoutSelectStatus.setOnClickListener{
                viewModel.postUpdateStatus(1)
            }
        }
        binding.layoutFree.apply {
            ivSelectStatus.setImageResource(R.drawable.ic_free)
            tvSelectStatus.text = getString(R.string.free)
            layoutSelectStatus.setOnClickListener{
                viewModel.postUpdateStatus(2)
            }
        }
        binding.layoutBusy.apply {
            ivSelectStatus.setImageResource(R.drawable.ic_busy)
            tvSelectStatus.text = getString(R.string.busy)
            layoutSelectStatus.setOnClickListener{
                viewModel.postUpdateStatus(3)
            }
        }
        binding.layoutNotAtSchool.apply {
            ivSelectStatus.setImageResource(R.drawable.ic_not_at_school)
            tvSelectStatus.text = getString(R.string.not_at_school)
            layoutSelectStatus.setOnClickListener{
                viewModel.postUpdateStatus(0)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "StatusChangeFragment"
    }
}