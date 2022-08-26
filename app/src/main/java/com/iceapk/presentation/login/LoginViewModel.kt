package com.iceapk.presentation.login
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iceapk.presentation.data.dto.LoginDTO
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
    val intent = Channel<Intent>(Channel.UNLIMITED)
    private val _state = MutableStateFlow<LoginViewState>(LoginViewState.Idle)
    val viewState: StateFlow<LoginViewState> = _state

    init {
        handleIntent()
    }


    private fun handleIntent() {
        viewModelScope.launch {
            intent.consumeAsFlow().collect {
                when (it){
                    is Intent.Login -> login(user = it.login )
                }
            }
        }
    }


    fun login(user: LoginDTO){
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = LoginViewState.Loading
            try {
                val resp = loginRepo.login( user)
                if (resp.response_code == 200){
                    _state.value = LoginViewState.Success(resp)
                }
                else{
                    _state.value = LoginViewState.Error("Login Failed. Username or password Incorrect")
                }
            } catch (e: Exception){
                _state.value = LoginViewState.Error("Login Failed. Username or password Incorrect")
            }
        }
    }

}