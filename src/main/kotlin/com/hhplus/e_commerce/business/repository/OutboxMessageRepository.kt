package com.hhplus.e_commerce.business.repository

import com.hhplus.e_commerce.business.entity.OutboxMessage

interface OutboxMessageRepository {

    fun save(outboxMessage: OutboxMessage): OutboxMessage

    fun findById(outboxMessageId: Long): OutboxMessage?

    fun findByStatus(eventType: String): List<OutboxMessage>
}