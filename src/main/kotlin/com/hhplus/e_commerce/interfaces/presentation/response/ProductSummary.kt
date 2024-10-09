package com.hhplus.e_commerce.interfaces.presentation.response

data class ProductSummary(
    val productId: Long,
    val name: String,
    val category: String,
    val price: Int,
    val salesCount: Int
)
