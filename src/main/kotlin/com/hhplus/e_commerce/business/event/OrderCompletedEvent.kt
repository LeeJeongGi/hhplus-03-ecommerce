package com.hhplus.e_commerce.business.event

import com.hhplus.e_commerce.business.dto.PaymentSaveResultDto
import org.springframework.context.ApplicationEvent

class OrderCompletedEvent(
    val paymentSaveResultDto: PaymentSaveResultDto
): ApplicationEvent(PaymentSaveResultDto) {
}