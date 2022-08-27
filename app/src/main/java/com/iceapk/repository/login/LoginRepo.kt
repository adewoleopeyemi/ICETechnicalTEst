package com.iceapk.repository.login

import com.iceapk.data.dto.LoginResp
import com.iceapk.data.models.Login

interface LoginRepo {
    suspend fun login(user: Login): LoginResp
}