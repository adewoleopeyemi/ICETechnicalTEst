package com.iceapk.presentation.profile.viewstate

import com.iceapk.data.dao.entities.Product
import com.iceapk.data.dto.SignupResp

sealed class ProfileViewState {
    object Idle: ProfileViewState()
    object Loading: ProfileViewState()
    data class Success(val  product: SignupResp): ProfileViewState()
    data class Error(val error: String?): ProfileViewState()
}