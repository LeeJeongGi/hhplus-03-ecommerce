package com.hhplus.e_commerce.business.stub

import com.hhplus.e_commerce.business.entity.Order
import java.time.LocalDateTime

object OrderStub {

    fun create(
        userId: Long = 0,
        status: String = "ORDERED",
        totalAmount: Int = 10000,
        orderDate: LocalDateTime = LocalDateTime.now(),
    ): Order {
        return Order(
            userId = userId,
            status = status,
            totalAmount = totalAmount,
            orderDate = orderDate,
        )
    }
}