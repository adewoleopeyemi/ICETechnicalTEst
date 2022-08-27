package com.iceapk.presentation.signup.intent

import com.iceapk.data.models.User


sealed class SignupIntent{
    data class signUp(val user: User): SignupIntent()
}
