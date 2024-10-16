package com.hhplus.e_commerce.business.event.dto

data class OrderCompletedEventDto(
    val orderId: Long,
    val userId: Long,
    val amount: Double
)