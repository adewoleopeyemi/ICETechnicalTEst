package com.iceapk.presentation.login.intent


sealed class LoginIntent{
    data class Login(var user: com.iceapk.data.models.Login): LoginIntent()
}
