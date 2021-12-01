package com.websarva.wings.android.flat.viewmodel

import androidx.lifecycle.ViewModel
import com.websarva.wings.android.flat.FLATApplication
import com.websarva.wings.android.flat.repository.ApiRepository

class UserSettingViewModel : ViewModel() {
    private val apiRepository = ApiRepository.instance
    private val roomRepository = FLATApplication.userRoomRepository


}