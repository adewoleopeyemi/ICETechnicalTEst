package com.iceapk.repository.signup

import com.iceapk.data.dto.SignupResp
import com.iceapk.data.models.User


interface SignupRepo {
    suspend fun signUp(user: User): SignupResp

}