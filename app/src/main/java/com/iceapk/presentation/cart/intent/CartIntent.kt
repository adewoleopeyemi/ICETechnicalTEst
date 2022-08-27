package com.iceapk.presentation.cart.intent

sealed class CartIntent{
    object getProductsFromCart: CartIntent()
}
