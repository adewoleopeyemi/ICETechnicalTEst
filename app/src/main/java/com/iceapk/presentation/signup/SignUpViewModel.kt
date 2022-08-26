package com.iceapk.presentation.signup

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iceapk.presentation.data.models.User
import com.iceapk.presentation.signup.intent.SignupIntent
import com.iceapk.presentation.signup.viewstate.SignupViewState
import com.iceapk.repository.signup.SignupRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(private val signupRepo: SignupRepo): ViewModel() {
    val signUpIntent = Channel<SignupIntent>(Channel.UNLIMITED)
    private val _signUpState = MutableStateFlow<SignupViewState>(SignupViewState.Idle)
    val signUpState: StateFlow<SignupViewState> = _signUpState


    init {
        handleIntent()
    }


    private fun handleIntent() {
        viewModelScope.launch {
            signUpIntent.consumeAsFlow().collect{
                when(it){
                    is SignupIntent.signUp -> signUp( it.user)
                }
            }
        }
    }

    fun signUp(user: User){
        viewModelScope.launch(Dispatchers.IO) {
            _signUpState.value = SignupViewState.Loading
            try {
                val resp = signupRepo.signUp(user)
                _signUpState.value = SignupViewState.Success(resp)
            }
            catch (e: Exception){
                _signUpState.value = SignupViewState.Error(""+e.message)
            }
        }
    }
}