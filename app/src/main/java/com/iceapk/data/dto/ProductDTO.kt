package com.iceapk.data.dto

import com.squareup.moshi.Json

data class ProductDTO(
    @Json(name="id") var id: Int,
    @Json(name="title") var title: String,
    @Json(name="price")  var price: Float,
    @Json(name="category") var category: String,
    @Json(name="description")  var description: String,
    @Json(name="image") var image: String,
    @Json(name="rating") var rating: RatingDTO
)

data class RatingDTO(
    @Json(name="rate")  var rate: Float,
    @Json(name="count")  var counts: Int
)
