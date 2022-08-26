package com.iceapk.repository.home

import com.iceapk.presentation.data.models.Product

interface HomeRepo {
    suspend fun getAllProducts(): List<Product>
}