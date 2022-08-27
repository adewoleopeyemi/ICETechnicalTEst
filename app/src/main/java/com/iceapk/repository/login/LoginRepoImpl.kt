package com.iceapk.repository.login

import com.iceapk.network.interfaces.ICEService
import com.iceapk.data.dto.LoginDTO
import com.iceapk.data.dto.LoginResp
import com.iceapk.data.models.Login
import javax.inject.Inject
import javax.inject.Named

class LoginRepoImpl
    @Inject constructor(@Named("ICE")private val service: ICEService): LoginRepo{


    override suspend fun login(user: Login): LoginResp {
        // Use dummy accounts provided to us by the api to login
        val dto = LoginDTO(username = "johnd", password = "m38rmF$")
        return service.loginUser(dto)
    }
}