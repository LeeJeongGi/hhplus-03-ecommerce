package com.hhplus.e_commerce.business.event

import com.fasterxml.jackson.databind.ObjectMapper
import com.hhplus.e_commerce.business.facade.dto.OutboxMessageDto
import org.springframework.context.ApplicationEvent

class OrderPaymentCompletedEvent(
    val outboxMessageDto: OutboxMessageDto
): ApplicationEvent(OutboxMessageDto) {

    private val objectMapper = ObjectMapper()

    fun toJson(): String = objectMapper.writeValueAsString(outboxMessageDto)

    companion object {
        fun from(outboxMessageDto: OutboxMessageDto): OrderPaymentCompletedEvent {
            return OrderPaymentCompletedEvent(outboxMessageDto)
        }
    }
}