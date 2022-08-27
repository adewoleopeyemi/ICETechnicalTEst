package com.iceapk.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.iceapk.data.dao.entities.Cart
import com.iceapk.data.dao.entities.Product

@Dao
interface CartsDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addToCart(product: Cart)

    @Query("select * from cart_table order by id asc")
    fun readCart(): List<Cart>
}