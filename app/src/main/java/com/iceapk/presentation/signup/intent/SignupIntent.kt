package com.iceapk.presentation.signup.intent


sealed class SignupIntent{
    data class signUp(val apiKey: String, val user: User): SignupIntent()
}
