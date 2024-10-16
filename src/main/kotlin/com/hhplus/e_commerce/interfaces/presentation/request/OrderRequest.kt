package com.hhplus.e_commerce.interfaces.presentation.request

data class OrderRequest(
    val userId: Long,
    val totalAmount: Int,
    val products: List<OrderProductRequest>
)
