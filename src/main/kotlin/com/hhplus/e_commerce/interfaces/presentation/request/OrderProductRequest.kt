package com.hhplus.e_commerce.interfaces.presentation.request

data class OrderProductRequest(
    val productStockId: Long,
    val quantity: Int,
)
