package com.iceapk.presentation.login
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iceapk.data.models.Login
import com.iceapk.presentation.login.intent.LoginIntent
import com.iceapk.presentation.login.viewstate.LoginViewState
import com.iceapk.repository.login.LoginRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private var loginRepo: LoginRepo): ViewModel() {
    val intent = Channel<LoginIntent>(Channel.UNLIMITED)
    private val _state = MutableStateFlow<LoginViewState>(LoginViewState.Idle)
    val viewState: StateFlow<LoginViewState> = _state

    init {
        handleIntent()
    }


    private fun handleIntent() {
        viewModelScope.launch {
            intent.consumeAsFlow().collect { it ->
                when (it){
                    is LoginIntent.Login -> login(it.user)
                }
            }
        }
    }


    fun login(user: Login){
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = LoginViewState.Loading
            try {
                val resp = loginRepo.login( user)
                _state.value = LoginViewState.Success(resp)
            } catch (e: Exception){
                Log.d("Debug", "login: "+e.message)
                _state.value = LoginViewState.Error("Login Failed. Username or password Incorrect")
            }
        }
    }

}