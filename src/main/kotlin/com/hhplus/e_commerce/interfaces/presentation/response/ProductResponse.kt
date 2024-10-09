package com.hhplus.e_commerce.interfaces.presentation.response

data class ProductResponse(
    val productId: Long,
    val name: String,
    val category: String,
    val price: Int,
    val stockQuantity: Int
)
