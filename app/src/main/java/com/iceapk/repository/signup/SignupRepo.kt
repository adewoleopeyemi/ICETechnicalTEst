package com.iceapk.repository.signup

import com.iceapk.presentation.data.dto.SignupResp
import com.iceapk.presentation.data.models.User


interface SignupRepo {
    suspend fun signUp(user: User): SignupResp

}