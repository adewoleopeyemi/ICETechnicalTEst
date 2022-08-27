package com.iceapk.data.dao.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_table")
data class Cart(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var pid: Int,
    var title: String,
    var price: Float,
    var category: String,
    var description: String,
    var image: String,
    var rating: Float
)