package com.iceapk.data.dto

import com.squareup.moshi.Json

data class CartDTO(
    @Json(name="products") var products: List<ProductDTO>
)
