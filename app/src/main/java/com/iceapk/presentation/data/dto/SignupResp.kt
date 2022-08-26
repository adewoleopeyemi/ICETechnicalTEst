package com.iceapk.presentation.data.dto

import com.squareup.moshi.Json

data class SignupResp(
    @Json(name="id") var id: String,
    @Json(name="email") var email: String,
    @Json(name="username") var username: String,
    @Json(name="password") var password: String,
    @Json(name="name") var name: NameDTO,
    @Json(name="address") var address: AddressDTO = AddressDTO(),
    @Json(name="phone") var phone: String
)