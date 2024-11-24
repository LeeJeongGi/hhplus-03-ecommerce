package com.hhplus.e_commerce.infra.impl

import com.hhplus.e_commerce.business.entity.OutboxMessage
import com.hhplus.e_commerce.business.repository.OutboxMessageRepository
import com.hhplus.e_commerce.infra.jpa.OutboxMessageJpaRepository
import org.springframework.stereotype.Repository
import kotlin.jvm.optionals.getOrNull

@Repository
class OutboxMessageRepositoryImpl(
    private val outboxMessageJpaRepository: OutboxMessageJpaRepository
): OutboxMessageRepository {

    override fun save(outboxMessage: OutboxMessage): OutboxMessage {
        return outboxMessageJpaRepository.save(outboxMessage)
    }

    override fun findById(outboxMessageId: Long): OutboxMessage? {
        return outboxMessageJpaRepository.findById(outboxMessageId).getOrNull()
    }

    override fun findByStatus(eventType: String): List<OutboxMessage> {
        return outboxMessageJpaRepository.findByStatus(eventType)
    }
}