package com.hhplus.e_commerce.common.messaging

import com.hhplus.e_commerce.business.facade.dto.OutboxMessageDto

interface MessagePublisher {

    fun publish(outboxMessageDto: OutboxMessageDto)

    fun publish(payload: String)
}