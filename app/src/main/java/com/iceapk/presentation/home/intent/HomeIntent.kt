package com.iceapk.presentation.home.intent

sealed class HomeIntent {
    object getData: HomeIntent()
    data class getProductsByCategory(var category: String): HomeIntent()
}