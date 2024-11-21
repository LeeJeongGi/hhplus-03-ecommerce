package com.hhplus.e_commerce.business.event

import com.hhplus.e_commerce.business.entity.type.OutboxStatus
import com.hhplus.e_commerce.business.service.OutboxMessageService
import com.hhplus.e_commerce.common.messaging.MessagePublisher
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class KafkaPublisherListener(
    private val outboxMessageService: OutboxMessageService,
    private val messagePublisher: MessagePublisher,
) {

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun publishToKafka(event: OrderPaymentCompletedEvent) {

        println("AFTER_COMMIT 단계: Kafka 메시지 발행: ${event.outboxMessageDto}")
        try {
            // Kafka 메시지 발행
            messagePublisher.publish(event.outboxMessageDto)

            // 상태 업데이트: SUCCESS
            outboxMessageService.updateStatus(event.outboxMessageDto.outboxMessageId, OutboxStatus.SUCCESS)
            println("AFTER_COMMIT 단계: Kafka 메시지 발행 성공 (SUCCESS 상태)")
        } catch (ex: Exception) {
            // 상태 업데이트: FAIL
            outboxMessageService.updateStatus(event.outboxMessageDto.outboxMessageId, OutboxStatus.FAIL)
            println("AFTER_COMMIT 단계: Kafka 메시지 발행 실패 (FAIL 상태)")
        }
    }

}