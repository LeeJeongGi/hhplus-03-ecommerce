package com.hhplus.e_commerce.business.dto

import com.hhplus.e_commerce.interfaces.presentation.request.OrderProductRequest

data class ProductOrderDto(
    val productStockId: Long,
    val quantity: Int,
) {
    companion object {
        fun from(orderProductRequest: OrderProductRequest): ProductOrderDto {
            return ProductOrderDto(
                productStockId = orderProductRequest.productStockId,
                quantity = orderProductRequest.quantity,
            )
        }
    }
}
