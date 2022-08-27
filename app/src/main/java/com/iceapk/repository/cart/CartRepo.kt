package com.iceapk.repository.cart

import com.iceapk.data.dao.entities.Product

interface CartRepo {
    suspend fun getProductsInCarts(): List<Product>
}