package com.iceapk.repository.home

import com.iceapk.data.dao.CartsDao
import com.iceapk.data.dao.ProductsDao
import com.iceapk.data.dao.entities.Cart
import com.iceapk.network.interfaces.ICEService
import com.iceapk.data.dao.entities.Product
import com.squareup.moshi.Json
import javax.inject.Inject
import javax.inject.Named

class HomeRepoImpl
    @Inject constructor(@Named("ICE") private val service: ICEService , private val productsDao: ProductsDao, private val cartsDao: CartsDao): HomeRepo {
    override suspend fun getAllProducts() {
        val productsDTO = service.getAllProducts()
        productsDTO.map {
            val product = Product(pid=it.id, title = it.title, description = it.description, price = it.price, image = it.image, category = it.category, rating =it.rating.rate)
            productsDao.addProduct(product)
        }
    }
    override suspend fun searchByCategory(category: String) : List<Product>{
       return  productsDao.getIProductByCategory(category)
    }

    override suspend fun addToCart(cart: Cart) {
        cartsDao.addToCart(cart)
    }

    override suspend fun readCart(): List<Cart> {
        return cartsDao.readCart()
    }
}