package com.iceapk.presentation.login

import com.avivimp.datamanager.models.LoginResp

sealed class LoginViewState {
    object Idle: LoginViewState()
    object Loading: LoginViewState()
    data class Success(val items: LoginResp): LoginViewState()
    data class Error(val error: String?): LoginViewState()
}