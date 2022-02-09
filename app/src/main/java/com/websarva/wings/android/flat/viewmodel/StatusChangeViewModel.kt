package com.websarva.wings.android.flat.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel

class StatusChangeViewModel: ViewModel() {

    fun onClickStatusChange(status: Int) {
        when (status) {
            0 -> {
                Log.d("statusClicked", "学校にいない")
            }
            1 -> {
                Log.d("statusClicked", "学校にいる")
            }
            2 -> {
                Log.d("statusClicked", "今暇")
            }
            3 -> {
                Log.d("statusClicked", "忙しい")
            }
        }
    }
}