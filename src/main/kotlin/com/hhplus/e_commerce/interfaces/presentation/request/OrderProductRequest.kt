package com.hhplus.e_commerce.interfaces.presentation.request

data class OrderProductRequest(
    val productId: Long,
    val quantity: Int
)
