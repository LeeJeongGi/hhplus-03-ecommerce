package com.hhplus.e_commerce.business.stub

import com.hhplus.e_commerce.business.entity.Payment
import java.time.LocalDateTime

object PaymentStub {

    fun create(
        userId: Long = 1L,
        orderId: Long = 1L,
        amount: Int = 1000,
        status: String = "COMPLETED",
        paymentDate: LocalDateTime = LocalDateTime.now()
    ): Payment {
        return Payment(
            userId = userId,
            orderId = orderId,
            amount = amount,
            status = status,
            paymentDate = paymentDate
        )
    }
}