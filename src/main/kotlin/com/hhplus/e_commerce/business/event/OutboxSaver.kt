package com.hhplus.e_commerce.business.event

import com.hhplus.e_commerce.business.entity.OutboxMessage
import com.hhplus.e_commerce.business.service.OutboxMessageService
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class OutboxSaver(
    private val outboxMessageService: OutboxMessageService,
) {

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    fun saveToOutbox(event: OrderPaymentCompletedEvent) {
        println("BEFORE_COMMIT 단계: Outbox 테이블에 저장: ${event.outboxMessageDto}")
        val saveOutboxMessage = outboxMessageService.save(OutboxMessage.from(event.toJson()))
        event.outboxMessageDto.outboxMessageId = saveOutboxMessage.id
    }
}