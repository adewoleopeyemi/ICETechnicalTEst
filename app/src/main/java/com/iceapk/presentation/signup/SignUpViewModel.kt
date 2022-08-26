package com.iceapk.presentation.signup

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.avivimp.datamanager.dtos.SignUpDTO
import com.avivimp.datamanager.models.Otp
import com.avivimp.network.responses.PhoneResponse
import com.avivimp.datamanager.models.Phone
import com.avivimp.datamanager.models.SignUpResp
import com.avivimp.datamanager.models.User
import com.avivimp.network.responses.OtpResponse
import com.avivimp.presentation.login.Intent
import com.avivimp.presentation.login.LoginViewState
import com.avivimp.presentation.otp.intents.OtpIntent
import com.avivimp.presentation.otp.intents.PhoneIntent
import com.avivimp.presentation.otp.viewstates.OtpViewState
import com.avivimp.presentation.otp.viewstates.PhoneViewState
import com.avivimp.presentation.signup.intent.SignupIntent
import com.avivimp.presentation.signup.viewstate.SignupViewState
import com.avivimp.repository.signup.SignupRepo
import com.avivimp.utils.decrypt
import com.google.gson.Gson
import com.iceapk.presentation.signup.intent.SignupIntent
import com.iceapk.presentation.signup.viewstate.SignupViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.sign

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
                    is SignupIntent.signUp -> signUp(it.apiKey, it.user)
                }
            }
        }
    }

    fun signUp(apiKey: String, user: User){
        viewModelScope.launch(Dispatchers.IO) {
            _signUpState.value = SignupViewState.Loading
            try {
                val resp = signupRepo.signUp(apiKey, user)
                val encrytedJson = resp.response_message
                val decrytedJson = encrytedJson.decrypt()
                if (resp.response_code == 200){
                    var signupBody = Gson().fromJson(decrytedJson, SignUpDTO::class.java)
                    var signUpResp: SignUpResp?
                    signupBody.apply {
                        signUpResp = SignUpResp(user.firstName, user.email, phoneNumber, accessToken, refreshToken)
                    }
                    _signUpState.value = SignupViewState.Success(signUpResp!!)
                }
                else{
                    _signUpState.value = SignupViewState.Error(decrytedJson!!)
                }
            }
            catch (e: Exception){
                _signUpState.value = SignupViewState.Error(""+e.message)
            }
        }
    }
}