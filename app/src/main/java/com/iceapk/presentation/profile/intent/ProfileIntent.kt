package com.iceapk.presentation.profile.intent

import com.iceapk.data.models.User

sealed class ProfileIntent{
    data class updateUserProfile( var user: User): ProfileIntent()
}
