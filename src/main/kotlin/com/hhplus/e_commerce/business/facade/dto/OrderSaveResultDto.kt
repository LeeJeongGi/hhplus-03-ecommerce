package com.hhplus.e_commerce.business.facade.dto

import com.hhplus.e_commerce.business.entity.Order

data class OrderSaveResultDto(
    val orderId: Long,
    val userId: Long,
    val totalAmount: Int
) {
    companion object {
        fun from(saveOrder: Order): OrderSaveResultDto {
            return OrderSaveResultDto(
                orderId = saveOrder.id,
                userId = saveOrder.userId,
                totalAmount = saveOrder.totalAmount,
            )
        }
    }
}
