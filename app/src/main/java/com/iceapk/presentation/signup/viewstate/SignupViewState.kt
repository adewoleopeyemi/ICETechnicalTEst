package com.iceapk.presentation.signup.viewstate

import com.iceapk.presentation.data.dto.SignupResp


sealed class SignupViewState {
    object Idle: SignupViewState()
    object Loading: SignupViewState()
    data class Success(val resp: SignupResp): SignupViewState()
    data class Error(val error: String): SignupViewState()
}