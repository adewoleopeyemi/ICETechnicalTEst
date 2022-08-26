package com.iceapk.repository.login

import com.iceapk.network.interfaces.ICEService
import com.iceapk.presentation.data.dto.LoginDTO
import com.iceapk.presentation.data.dto.LoginResp
import com.iceapk.presentation.data.models.Login
import javax.inject.Inject
import javax.inject.Named

class LoginRepoImpl
    @Inject constructor(@Named("ICE")private val service: ICEService): LoginRepo{


    override suspend fun login(user: Login): LoginResp {
        val dto = LoginDTO(username = user.username, password = user.password)
        return service.loginUser(dto)
    }
}