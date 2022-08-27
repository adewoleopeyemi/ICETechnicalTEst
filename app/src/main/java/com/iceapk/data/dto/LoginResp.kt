package com.iceapk.data.dto

import com.squareup.moshi.Json

data class LoginResp(
    @Json(name="token") var token: String,
)