package com.iceapk.presentation.login


sealed class Intent{
    data class Login(var apiKey: String, var user: LoginDTO): Intent()
}
