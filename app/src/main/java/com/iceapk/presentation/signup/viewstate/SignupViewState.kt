package com.iceapk.presentation.signup.viewstate


sealed class SignupViewState {
    object Idle: SignupViewState()
    object Loading: SignupViewState()
    data class Success(val resp: SignUpResp): SignupViewState()
    data class Error(val error: String): SignupViewState()
}