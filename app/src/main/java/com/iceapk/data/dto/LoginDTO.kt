package com.iceapk.data.dto

import com.squareup.moshi.Json

data class LoginDTO (
    @Json(name="username") var username: String,
    @Json(name="password") var password: String,
)