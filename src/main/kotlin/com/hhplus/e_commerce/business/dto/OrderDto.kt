package com.hhplus.e_commerce.business.dto

import com.hhplus.e_commerce.business.entity.Order
import java.time.LocalDateTime

data class OrderDto(
    val orderId: Long,
    val userId: Long,
    val status: String,
    val totalAmount: Int,
    val orderDate: LocalDateTime,
) {
    companion object {
        fun from(order: Order): OrderDto {
            return OrderDto(
                orderId = order.id,
                userId = order.userId,
                status = order.status,
                totalAmount = order.totalAmount,
                orderDate = order.orderDate,
            )
        }
    }

}
