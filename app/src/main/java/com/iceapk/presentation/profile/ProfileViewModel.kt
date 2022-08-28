package com.iceapk.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iceapk.data.models.User
import com.iceapk.presentation.home.intent.HomeIntent
import com.iceapk.presentation.home.viewstates.HomeViewState
import com.iceapk.presentation.profile.intent.ProfileIntent
import com.iceapk.presentation.profile.viewstate.ProfileViewState
import com.iceapk.repository.profile.ProfileRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(private val repo: ProfileRepo): ViewModel() {

    val intent = Channel<ProfileIntent>(Channel.UNLIMITED)
    private val _state = MutableStateFlow<ProfileViewState>(ProfileViewState.Idle)
    val viewState: StateFlow<ProfileViewState> = _state
    init{
        handleIntent()
    }
    private fun handleIntent() {
        viewModelScope.launch(Dispatchers.IO) {
            intent.consumeAsFlow().collect { it ->
                when (it){
                    is ProfileIntent.updateUserProfile -> updateProfile(it.user)
                }
            }
        }
    }

    private suspend fun updateProfile(user: User){
        _state.value = ProfileViewState.Loading
        try{
            val resp = repo.updateProfile(user)
            _state.value = ProfileViewState.Success(resp)
        }
        catch (e: Exception){
            _state.value = ProfileViewState.Error("Couldn't update profile. PLease try again")
        }
    }

}