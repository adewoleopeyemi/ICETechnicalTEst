package com.iceapk.presentation.home.intent

import com.iceapk.data.dao.entities.Cart

sealed class HomeIntent {
    object getData: HomeIntent()
    data class getProductsByCategory(var category: String): HomeIntent()
    data class addToCart(var cart: Cart): HomeIntent()
}