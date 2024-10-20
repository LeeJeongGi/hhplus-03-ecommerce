package com.hhplus.e_commerce.interfaces.presentation.response

import com.hhplus.e_commerce.business.facade.dto.OrderSaveResultDto

data class OrderResponse(
    val orderId: Long,
    val userId: Long,
    val totalAmount: Int
) {
    companion object {
        fun from(result: OrderSaveResultDto): OrderResponse {
            return OrderResponse(
                orderId = result.orderId,
                userId = result.userId,
                totalAmount = result.totalAmount,
            )
        }
    }
}
