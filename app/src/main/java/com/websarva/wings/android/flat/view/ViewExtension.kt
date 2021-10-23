package com.websarva.wings.android.flat.view

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("android:onSafeClick")
fun View.setOnSafeClickListener(listener: View.OnClickListener) {
    // 押下処理を無効にする時間
    val delay = 1000L
    // 前回押下時間
    var pushed = 0L

    this.setOnClickListener {
        if (System.currentTimeMillis() - pushed < delay) {
            return@setOnClickListener
        }
        // 押下時間を更新
        pushed = System.currentTimeMillis()
        // 押下処理を実行
        listener.onClick(this)
        return@setOnClickListener
    }
}