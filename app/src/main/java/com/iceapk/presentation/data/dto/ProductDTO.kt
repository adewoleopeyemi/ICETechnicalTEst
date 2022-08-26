package com.iceapk.presentation.data.dto

import com.squareup.moshi.Json

data class ProductDTO(
    @Json(name="productId") var id: String,
    @Json(name="quantity") var quantity: String,
)
