package com.websarva.wings.android.flat.view

import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.websarva.wings.android.flat.R

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

@BindingAdapter("android:setStatus")
fun setStatus(view: ImageView, status: Int) {
    when(status) {
        0 -> view.setImageDrawable(ContextCompat.getDrawable(view.context, R.drawable.ic_not_at_school))
        1 -> view.setImageDrawable(ContextCompat.getDrawable(view.context, R.drawable.ic_at_school))
        2 -> view.setImageDrawable(ContextCompat.getDrawable(view.context, R.drawable.ic_free))
        3 -> view.setImageDrawable(ContextCompat.getDrawable(view.context, R.drawable.ic_busy))
    }
}