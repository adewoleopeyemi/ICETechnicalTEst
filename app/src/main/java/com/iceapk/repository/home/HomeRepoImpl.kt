package com.iceapk.repository.home

import com.iceapk.network.interfaces.ICEService
import com.iceapk.data.dao.entities.Product
import com.squareup.moshi.Json
import javax.inject.Inject
import javax.inject.Named

class HomeRepoImpl
    @Inject constructor(@Named("ICE") private val service: ICEService): HomeRepo {
    override suspend fun getAllProducts(): List<Product> {
        val productsDTO = service.getCart().products
        val products = arrayListOf<Product>()
        productsDTO.map {
            val product = Product(pid=it.id, title = it.title, description = it.description, price = it.price, image = it.image, category = it.category)
            products.add(product)
        }
        return products
    }
}