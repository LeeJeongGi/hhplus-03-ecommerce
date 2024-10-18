package com.hhplus.e_commerce.interfaces.presentation.response

import com.hhplus.e_commerce.business.dto.PaymentSaveResultDto
import java.time.LocalDateTime

data class PaymentResponse(
    val userId: Long,
    val orderId: Long,
    val status: String,
    val amount: Int,
    val paymentDate: LocalDateTime,
) {
    companion object {
        fun from(result: PaymentSaveResultDto): PaymentResponse? {
            return PaymentResponse(
                userId = result.userId,
                orderId = result.orderId,
                status = result.status,
                amount = result.amount,
                paymentDate = result.paymentDate,
            )
        }
    }

}
