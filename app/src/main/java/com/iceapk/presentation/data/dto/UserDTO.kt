package com.iceapk.presentation.data.dto

import com.squareup.moshi.Json

data class UserDTO(
    @Json(name="email") var email: String,
    @Json(name="username") var username: String,
    @Json(name="password") var password: String,
    @Json(name="name") var name: NameDTO,
    @Json(name="address") var address: AddressDTO = AddressDTO(),
    @Json(name="phone") var phone: String
)
data class  NameDTO(
    @Json(name="firstname") var firstname: String,
    @Json(name="lastname") var lastname: String,
)

data class AddressDTO(
    @Json(name="city") var city: String ="Lagos",
    @Json(name="street") var street: String = "No 10 Muyideen Adeoye Street Ikeja",
    @Json(name="zipcode") var zipcode: String = "100212",
    @Json(name="geolocation") var geolocation:  GeolocationDTO = GeolocationDTO()
)

data class GeolocationDTO(
    @Json(name="lat") var lat: String = "6.6018",
    @Json(name="long") var long: String = "3.3515"
)