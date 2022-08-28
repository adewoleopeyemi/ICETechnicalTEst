package com.iceapk.presentation.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iceapk.presentation.cart.intent.CartIntent
import com.iceapk.presentation.cart.viewstate.CartViewState
import com.iceapk.presentation.home.intent.HomeIntent
import com.iceapk.presentation.home.viewstates.HomeViewState
import com.iceapk.repository.cart.CartRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel  @Inject constructor(private val repo: CartRepo): ViewModel(){
    val intent = Channel<CartIntent>(Channel.UNLIMITED)
    private val _state = MutableStateFlow<CartViewState>(CartViewState.Idle)
    val viewState: StateFlow<CartViewState> = _state
    init{
        handleIntent()
    }
    private fun handleIntent() {
        viewModelScope.launch(Dispatchers.IO) {
            intent.consumeAsFlow().collect { it ->
                when (it){
                    is CartIntent.getProductsFromCart -> getProducts()
                }
            }
        }
    }

    private suspend  fun getProducts() {
        _state.value = CartViewState.Loading
        try{
            val products = repo.getProductsInCarts()
            _state.value =CartViewState.Success(products)
        }catch(e: Exception){
            _state.value = CartViewState.Error("Couldn't fetch products in carts. Please try again")
        }
    }
}