package com.iceapk.network.interfaces

import com.iceapk.presentation.data.dto.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ICEService {
    @POST("users")
    suspend fun registerUser(@Body user: UserDTO): SignupResp

    @POST("auth/login")
    suspend fun loginUser(@Body user: LoginDTO): LoginResp

    @GET("carts/5")
    suspend fun getCart(): CartDTO
}