package com.iceapk.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.iceapk.data.dao.entities.Product


@Dao
interface ProductsDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addProduct(product: Product)

    @Query("select * from product_table order by id asc")
    fun readAllProducts(): List<Product>

    @Update
    suspend fun updateCollection(product: Product)

    @Query("select * from product_table where category like :query")
    fun getIProductByCategory(query: String): List<Product>
}