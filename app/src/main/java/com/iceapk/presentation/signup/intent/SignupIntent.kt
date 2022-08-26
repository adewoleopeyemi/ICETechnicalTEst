package com.iceapk.presentation.signup.intent

import com.iceapk.presentation.data.models.User


sealed class SignupIntent{
    data class signUp(val apiKey: String, val user: User): SignupIntent()
}
