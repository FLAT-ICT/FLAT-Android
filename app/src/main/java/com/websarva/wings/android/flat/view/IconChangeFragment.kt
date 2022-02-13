package com.websarva.wings.android.flat.view

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.websarva.wings.android.flat.R
import com.websarva.wings.android.flat.databinding.FragmentIconChangeBinding

class IconChangeFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentIconChangeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIconChangeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.layoutSelectImage.apply {
            tvSelectIcon.text = getString(R.string.select_image)
            layoutSelectIcon.setOnClickListener {
                // TODO: 選択時の処理
            }
        }
        binding.layoutRemoveImage.apply {
            tvSelectIcon.text = getString(R.string.remove_image)
            tvSelectIcon.setTextColor(requireContext().getColor(R.color.red))
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        _binding = null
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        _binding = null
    }

    companion object {
        const val TAG = "IconChangeFragment"
    }
}