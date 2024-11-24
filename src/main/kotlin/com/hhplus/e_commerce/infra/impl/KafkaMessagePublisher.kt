package com.hhplus.e_commerce.infra.impl

import com.fasterxml.jackson.databind.ObjectMapper
import com.hhplus.e_commerce.business.facade.dto.OutboxMessageDto
import com.hhplus.e_commerce.common.messaging.MessagePublisher
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class KafkaMessagePublisher(
    private val kafkaTemplate: KafkaTemplate<String, String>,
    private val objectMapper: ObjectMapper,
): MessagePublisher {

    /**
     * Test Api 사용시 호출되는 method
     */
    override fun publish(outboxMessageDto: OutboxMessageDto) {
        val topic = "payment-events"
        val payload = objectMapper.writeValueAsString(outboxMessageDto)

        kafkaTemplate.send(topic, payload)
        println("Kafka 메시지 발행 완료: $payload")
    }

    override fun publish(payload: String) {
        val topic = "payment-events"

        kafkaTemplate.send(topic, payload)
        println("Kafka 메시지 발행 완료: $payload")
    }
}