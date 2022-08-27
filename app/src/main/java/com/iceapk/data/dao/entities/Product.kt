package com.iceapk.data.dao.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product_table")
data class Product(
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