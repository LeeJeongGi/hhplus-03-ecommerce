package com.hhplus.e_commerce.business.dto

import com.hhplus.e_commerce.business.entity.Payment
import java.time.LocalDateTime

data class PaymentSaveResultDto(
    val paymentId: Long,
    val userId: Long,
    val orderId: Long,
    val status: String,
    val amount: Int,
    val paymentDate: LocalDateTime,
) {
    companion object {
        fun from(payment: Payment): PaymentSaveResultDto {
            return PaymentSaveResultDto(
                paymentId = payment.id,
                userId = payment.userId,
                orderId = payment.orderId,
                status = payment.status,
                amount = payment.amount,
                paymentDate = payment.paymentDate,
            )
        }
    }

}
