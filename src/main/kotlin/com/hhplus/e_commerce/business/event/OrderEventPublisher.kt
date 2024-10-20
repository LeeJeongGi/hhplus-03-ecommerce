package com.hhplus.e_commerce.business.event

import com.hhplus.e_commerce.business.dto.PaymentSaveResultDto
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component

@Component
class OrderEventPublisher(
    private val publisher: ApplicationEventPublisher,
) {

    fun publishDataPlatformEvent(paymentSaveResultDto: PaymentSaveResultDto) {
        val event = OrderCompletedEvent(paymentSaveResultDto)
        publisher.publishEvent(event)
    }
}