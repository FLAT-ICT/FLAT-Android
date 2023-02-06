package com.websarva.wings.android.flat.util

import android.content.Context

class SharedPreferencesService(private val context: Context) {
    companion object {
        private const val FILE_NAME = "Preferences"
        private const val USER_ID = "user_id"
        private const val USER_NAME = "user_name"
        private const val SPOT = "spot"
        private const val ICON_PATH = "icon_path"
        private const val LOGGED_IN_AT = "logged_in_at"
    }
}