package com.iceapk.repository.profile

import com.iceapk.data.dto.SignupResp
import com.iceapk.data.models.User

interface ProfileRepo {
    suspend fun updateProfile(user: User): SignupResp
}