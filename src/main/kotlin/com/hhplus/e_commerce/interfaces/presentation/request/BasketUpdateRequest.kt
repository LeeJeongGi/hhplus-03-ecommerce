package com.hhplus.e_commerce.interfaces.presentation.request

data class BasketUpdateRequest(
    val userId: Long,
    val productId: Long,
    val quantity: Int,
    val action: String // "ADD" 또는 "REMOVE"
)
