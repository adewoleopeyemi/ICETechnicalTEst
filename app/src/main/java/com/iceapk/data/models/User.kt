package com.iceapk.data.models


data class User(
    var email: String,
    var username: String,
    var password: String,
    var name: Name,
    var address: Address = Address(),
    var phone: String
)
data class  Name(
    var firstname: String,
    var lastname: String,
)

data class Address(
    var city: String ="Lagos",
    var street: String = "No 10 Muyideen Adeoye Street Ikeja",
    var zipcode: String = "100212",
    var geolocation:  Geolocation = Geolocation()
)

data class Geolocation(
    var lat: String = "6.6018",
    var long: String = "3.3515"
)