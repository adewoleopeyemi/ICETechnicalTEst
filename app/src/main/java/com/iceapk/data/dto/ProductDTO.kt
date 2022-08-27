package com.iceapk.data.dto

import com.squareup.moshi.Json

data class ProductDTO(
    @Json(name="productId") var id: Int,
    @Json(name="title") var title: String,
    @Json(name="price")  var price: String,
    @Json(name="category") var category: String,
    @Json(name="description")  var description: String,
    @Json(name="image") var image: String
)
