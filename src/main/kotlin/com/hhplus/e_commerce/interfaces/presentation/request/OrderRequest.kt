package com.hhplus.e_commerce.interfaces.presentation.request

data class OrderRequest(
    val userId: Long,
    val products: List<OrderProductRequest>
)
