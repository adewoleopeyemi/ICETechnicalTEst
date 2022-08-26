package com.iceapk.repository.login

import com.iceapk.presentation.data.dto.LoginResp
import com.iceapk.presentation.data.models.Login
import com.iceapk.presentation.data.models.User

interface LoginRepo {
    suspend fun login(user: Login): LoginResp
}