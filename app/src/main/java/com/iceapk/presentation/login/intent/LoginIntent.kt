package com.iceapk.presentation.login

import com.iceapk.presentation.data.models.Login


sealed class LoginIntent{
    data class Login(var user: com.iceapk.presentation.data.models.Login): LoginIntent()
}
