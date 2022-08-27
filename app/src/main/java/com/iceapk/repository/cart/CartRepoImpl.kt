package com.iceapk.repository.cart

import com.iceapk.data.dao.CartsDao
import com.iceapk.data.dao.entities.Product
import javax.inject.Inject

class CartRepoImpl @Inject constructor(private val cartsDao: CartsDao): CartRepo {
    override suspend fun getProductsInCarts(): List<Product> {
       val cartProducts = cartsDao.readCart()
        val products = arrayListOf<Product>()
        cartProducts.map { product->
            products.add(
                Product(pid=product.pid, title = product.title,
                    price = product.price, category = product.category, description = product.description,
                    image = product.image, rating = product.rating)
            )
        }
        return products
    }
}