package com.hhplus.e_commerce.business.stub

import com.hhplus.e_commerce.business.entity.OrderItem

object OrderItemStub {

    fun create(
        orderId: Long = 0,
        productStockId: Long = 0,
        quantity: Int = 1,
    ): OrderItem {
        return OrderItem(
            orderId = orderId,
            productStockId = productStockId,
            quantity = quantity,
        )
    }

    fun createMultiple(
        orderId: Long = 1L,
        sizesWithQuantities: List<Pair<Long, Int>> = listOf(
            1L to 1,
            2L to 2,
            3L to 3,
        )
    ): List<OrderItem> {
        return sizesWithQuantities.map { (productStockId, quantity) ->
            create(orderId, productStockId, quantity)
        }
    }

}