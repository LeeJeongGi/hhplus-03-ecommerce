package com.hhplus.e_commerce.business.facade.dto

import com.hhplus.e_commerce.business.dto.PaymentSaveResultDto

data class OutboxMessageDto(
    val paymentId: Long,
    val totalAmount: Int,
    var outboxMessageId: Long? = null,
) {

    companion object {
        fun from(result: PaymentSaveResultDto): OutboxMessageDto {
            return OutboxMessageDto(
                paymentId = result.paymentId,
                totalAmount = result.amount
            )
        }
    }

}
