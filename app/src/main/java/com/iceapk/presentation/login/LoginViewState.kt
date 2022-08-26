package com.iceapk.presentation.login

import com.iceapk.presentation.data.dto.LoginResp


sealed class LoginViewState {
    object Idle: LoginViewState()
    object Loading: LoginViewState()
    data class Success(val resp: LoginResp): LoginViewState()
    data class Error(val error: String?): LoginViewState()
}