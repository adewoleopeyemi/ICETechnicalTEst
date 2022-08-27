package com.iceapk.presentation.cart.viewstate

import com.iceapk.data.dao.entities.Product
import com.iceapk.presentation.home.viewstates.HomeViewState

sealed class CartViewState {
    object Idle: CartViewState()
    object Loading: CartViewState()
    data class Success(val  product: List<Product>): CartViewState()
    data class Error(val error: String?): CartViewState()
}