package com.hhplus.e_commerce.business.event

import com.hhplus.e_commerce.business.facade.dto.OutboxMessageDto
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component

@Component
class PaymentApiPublisher(
    private val publisher: ApplicationEventPublisher,
) {

    fun publishDataPlatformEvent(outboxMessageDto: OutboxMessageDto) {
        val event = OrderPaymentCompletedEvent.from(outboxMessageDto)
        publisher.publishEvent(event)
    }
}