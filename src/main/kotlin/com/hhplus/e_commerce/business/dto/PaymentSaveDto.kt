package com.hhplus.e_commerce.business.dto

import java.time.LocalDateTime

data class PaymentSaveDto(
    val userId: Long,
    val orderId: Long,
    val status: String,
    val amount: Int,
) {


}
