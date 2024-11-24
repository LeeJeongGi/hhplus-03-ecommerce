package com.hhplus.e_commerce.common.scheduler

import com.hhplus.e_commerce.business.entity.type.OutboxStatus
import com.hhplus.e_commerce.infra.impl.KafkaMessagePublisher
import com.hhplus.e_commerce.business.service.OutboxMessageService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Component
class OutboxMessageScheduler(
    private val outboxMessageService: OutboxMessageService,
    private val kafkaMessagePublisher: KafkaMessagePublisher,
) {

    @Scheduled(fixedRate = 300000)
    fun resendInitMessages() {

        val initMessages = outboxMessageService.findByStatus(OutboxStatus.READY.toString())
            .filter { message -> message.createdAt.isAfter(LocalDateTime.now().minusMinutes(5)) }

        initMessages.forEach { message ->
            try {

                kafkaMessagePublisher.publish(message.payload)

                message.update(OutboxStatus.SUCCESS)
                outboxMessageService.save(message)

                println("Kafka 메시지 재발행 성공: ${message.id}")
            } catch (ex: Exception) {

                message.update(OutboxStatus.FAIL)

                outboxMessageService.save(message)
                println("Kafka 메시지 재발행 실패: ${message.id}, 에러: ${ex.message}")
            }
        }
    }
}