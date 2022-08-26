package com.iceapk.repository.home

import com.iceapk.network.interfaces.ICEService
import com.iceapk.presentation.data.models.Product
import javax.inject.Inject
import javax.inject.Named

class HomeRepoImpl
    @Inject constructor(@Named("ICE") private val service: ICEService): HomeRepo {
    override suspend fun getAllProducts(): List<Product> {
        val productsDTO = service.getCart().products
        val products = arrayListOf<Product>()
        productsDTO.map {
            val product = Product(it.id, it.quantity)
            products.add(product)
        }
        return products
    }
}