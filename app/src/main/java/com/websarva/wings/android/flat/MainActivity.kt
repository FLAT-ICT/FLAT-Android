package com.websarva.wings.android.flat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.websarva.wings.android.flat.view.FriendListFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().replace(R.id.app_container, FriendListFragment()).commit()
    }
}