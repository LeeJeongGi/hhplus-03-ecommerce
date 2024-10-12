package com.hhplus.e_commerce.interfaces.presentation.response

data class OrderProduct(
    val productId: Long,
    val quantity: Int,
    val price: Int,
    val totalAmount: Int
)
