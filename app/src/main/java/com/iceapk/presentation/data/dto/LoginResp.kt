package com.iceapk.presentation.data.dto

import com.squareup.moshi.Json

data class LoginResp(
    @Json(name="token") var token: String,
)