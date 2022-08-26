package com.iceapk.network.interfaces

import com.iceapk.presentation.data.dto.LoginDTO
import com.iceapk.presentation.data.dto.LoginResp
import com.iceapk.presentation.data.dto.SignupResp
import com.iceapk.presentation.data.dto.UserDTO
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ICEService {
    @POST("users")
    suspend fun registerUser(@Body user: UserDTO): SignupResp

    @POST("user/api/v1/login")
    suspend fun loginUser(@Body user: LoginDTO): LoginResp
}