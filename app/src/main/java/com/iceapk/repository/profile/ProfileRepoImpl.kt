package com.iceapk.repository.profile

import com.iceapk.data.dto.NameDTO
import com.iceapk.data.dto.SignupResp
import com.iceapk.data.dto.UserDTO
import com.iceapk.data.models.User
import com.iceapk.network.interfaces.ICEService
import javax.inject.Inject
import javax.inject.Named

class ProfileRepoImpl @Inject
constructor(@Named("ICE") private val service: ICEService) : ProfileRepo{
    override suspend fun updateProfile(user: User): SignupResp {
        val dto = UserDTO(email = user.email, username = user.username,  password = user.password,  name = NameDTO(firstname = user.name.firstname, lastname = user.name.lastname),  phone = user.phone)
        return service.updateUser(dto)
    }

}