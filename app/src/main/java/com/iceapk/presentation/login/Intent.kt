package com.iceapk.presentation.login

import com.iceapk.presentation.data.dto.LoginDTO


sealed class Intent{
    data class Login(var login: LoginDTO): Intent()
}
