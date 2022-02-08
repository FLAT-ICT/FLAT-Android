package com.websarva.wings.android.flat.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.websarva.wings.android.flat.R
import com.websarva.wings.android.flat.databinding.FragmentStatusChangeBinding

class StatusChangeFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentStatusChangeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStatusChangeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.layoutAtSchool.apply {
            ivSelectStatus.setImageResource(R.drawable.ic_at_school)
            tvSelectStatus.text = getString(R.string.at_school)
        }
        binding.layoutFree.apply {
            ivSelectStatus.setImageResource(R.drawable.ic_free)
            tvSelectStatus.text = getString(R.string.free)
        }
        binding.layoutBusy.apply {
            ivSelectStatus.setImageResource(R.drawable.ic_busy)
            tvSelectStatus.text = getString(R.string.busy)
        }
        binding.layoutNotAtSchool.apply {
            ivSelectStatus.setImageResource(R.drawable.ic_not_at_school)
            tvSelectStatus.text = getString(R.string.not_at_school)
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