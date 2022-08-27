package com.iceapk.presentation.home.viewstates

import com.iceapk.data.dao.entities.Product


sealed class HomeViewState {
    object Idle: HomeViewState()
    object Loading: HomeViewState()
    data class Success(val  product: List<Product>): HomeViewState()
    data class Error(val error: String?): HomeViewState()
}