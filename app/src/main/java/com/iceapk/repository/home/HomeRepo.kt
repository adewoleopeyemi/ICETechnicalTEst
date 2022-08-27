package com.iceapk.repository.home

import com.iceapk.data.dao.entities.Cart
import com.iceapk.data.dao.entities.Product

interface HomeRepo {
    suspend fun getAllProducts()
    suspend fun searchByCategory(category: String): List<Product>
    suspend fun addToCart(cart: Cart)
    suspend fun readCart(): List<Cart>
}