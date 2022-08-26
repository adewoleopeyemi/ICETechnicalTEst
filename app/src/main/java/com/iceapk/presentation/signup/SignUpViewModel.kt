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

    val phoneIntent = Channel<PhoneIntent>(Channel.UNLIMITED)
    private val _phoneState = MutableStateFlow<PhoneViewState>(PhoneViewState.Idle)
    val phoneViewState: StateFlow<PhoneViewState> = _phoneState


    val otpIntent = Channel<OtpIntent>(Channel.UNLIMITED)
    private val _otpState = MutableStateFlow<OtpViewState>(OtpViewState.Idle)
    val otpViewState: StateFlow<OtpViewState> = _otpState


    init {
        handleIntent()
    }


    private fun handleIntent() {
        viewModelScope.launch {
            phoneIntent.consumeAsFlow().collect {
                when (it){
                    is PhoneIntent.verifyPhone -> verifyPhone(apiKey =it.apiKey, phone = it.phone, false)
                }
            }
        }
        viewModelScope.launch {
            otpIntent.consumeAsFlow().collect{
                when (it){
                    is OtpIntent.verifyOtp -> getVerify(it.apiKey, it.otp)
                    is OtpIntent.resendOtp -> verifyPhone(it.apiKey, it.phone, true)
                }
            }
        }

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

    private fun verifyPhone(apiKey: String, phone: Phone, forResend: Boolean){
        viewModelScope.launch(Dispatchers.IO) {
            if (forResend){
                _otpState.value = OtpViewState.ResendLoading
            }
            else{
                _phoneState.value = PhoneViewState.Loading
            }
            try{
                val resp = signupRepo.verifyPhone(apiKey, phone)
                if (resp.responseCode == 200){
                    if (forResend){
                        _otpState.value = OtpViewState.ResendSuccess(resp)
                    }else{
                        _phoneState.value = PhoneViewState.Success(resp)
                    }
                }
                else{
                    if (forResend){
                        _otpState.value = OtpViewState.ResendError("Invalid phone number")
                    }
                    else{
                        _phoneState.value = PhoneViewState.Error("Invalid phone number")
                    }
                }
            }
            catch (e: Exception){
                if (forResend){
                    _otpState.value = OtpViewState.ResendError("Invalid phone number")
                }
                else{
                    _phoneState.value = PhoneViewState.Error("Invalid phone number")
                }
            }
        }
    }

    private fun getVerify(apiKey: String, otp: Otp){
        viewModelScope.launch(Dispatchers.IO) {
            _otpState.value = OtpViewState.verifyLoading
            try{
                val resp = signupRepo.getVerify(apiKey, otp)
                if (resp.responseCode == 200){
                    if(resp.responseMessage.totp){
                        _otpState.value = OtpViewState.verifySuccess(resp)
                    }
                    else{
                        _otpState.value = OtpViewState.verifyError("Incorrect OTP")
                    }
                }
            }
            catch (e: Exception){
                _otpState.value = OtpViewState.verifyError("Incorrect OTP")
            }
        }
    }
}