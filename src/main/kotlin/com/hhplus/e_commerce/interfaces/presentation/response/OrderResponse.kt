package com.hhplus.e_commerce.interfaces.presentation.response

data class OrderResponse(
    val balance: Int,
    val orderId: Long,
    val products: List<OrderProduct>
)
