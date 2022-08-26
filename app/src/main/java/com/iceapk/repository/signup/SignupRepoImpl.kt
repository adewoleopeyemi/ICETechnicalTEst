package com.iceapk.repository.signup

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.iceapk.network.interfaces.ICEService
import com.iceapk.presentation.data.dto.NameDTO
import com.iceapk.presentation.data.dto.SignupResp
import com.iceapk.presentation.data.dto.UserDTO
import com.iceapk.presentation.data.models.User
import javax.inject.Inject
import javax.inject.Named

class SignupRepoImpl @Inject
    constructor(@Named("ICE") private val service: ICEService): SignupRepo {

    override suspend fun signUp(user: User): SignupResp{
        val dto = UserDTO(email = user.email, username = user.username,  password = user.password,  name = NameDTO(firstname = user.name.firstname, lastname = user.name.lastname),  phone = user.phone)
        return service.registerUser(dto)
    }
}