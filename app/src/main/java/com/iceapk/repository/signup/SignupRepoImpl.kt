package com.iceapk.repository.signup

import com.iceapk.network.interfaces.ICEService
import com.iceapk.data.dto.NameDTO
import com.iceapk.data.dto.SignupResp
import com.iceapk.data.dto.UserDTO
import com.iceapk.data.models.User
import javax.inject.Inject
import javax.inject.Named

class SignupRepoImpl @Inject
    constructor(@Named("ICE") private val service: ICEService): SignupRepo {

    override suspend fun signUp(user: User): SignupResp{
        val dto = UserDTO(email = user.email, username = user.username,  password = user.password,  name = NameDTO(firstname = user.name.firstname, lastname = user.name.lastname),  phone = user.phone)
        return service.registerUser(dto)
    }
}