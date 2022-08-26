package com.iceapk.presentation.login
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avivimp.datamanager.dtos.LoginDTO
import com.avivimp.datamanager.models.LoginResp
import com.avivimp.datamanager.models.User
import com.avivimp.repository.login.LoginRepo
import com.avivimp.utils.decrypt
import com.google.gson.Gson
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
                    is Intent.Login -> login(apiKey =it.apiKey, user = it.user )
                }
            }
        }
    }


    fun login(apiKey: String, user: LoginDTO){
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = LoginViewState.Loading
            try {
                val resp = loginRepo.login(apiKey, user)
                val encrytedJson = resp.response_message
                val decrytedJson = encrytedJson.decrypt()
                Log.d("Debug", "verifyNin: decrypted ${decrytedJson}")
                if (resp.response_code == 200){
                    val loginBody = Gson().fromJson(decrytedJson, LoginResp::class.java)
                    _state.value = LoginViewState.Success(loginBody)
                }
                else{
                    _state.value = LoginViewState.Error("Login Failed. Username or password Incorrect")
                }
            } catch (e: Exception){
                Log.d("Debug", "verifyNin: error ${e.message}")
                _state.value = LoginViewState.Error("Login Failed. Username or password Incorrect")
            }
        }
    }

}