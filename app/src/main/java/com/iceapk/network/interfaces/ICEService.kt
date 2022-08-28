package com.iceapk.network.interfaces

import com.iceapk.data.dto.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST

interface ICEService {
    @POST("users")
    suspend fun registerUser(@Body user: UserDTO): SignupResp

    @POST("auth/login")
    suspend fun loginUser(@Body user: LoginDTO): LoginResp

    @GET("products")
    suspend fun getAllProducts(): List<ProductDTO>

    @PATCH("users/7")
    suspend fun updateUser(@Body user: UserDTO): SignupResp
}