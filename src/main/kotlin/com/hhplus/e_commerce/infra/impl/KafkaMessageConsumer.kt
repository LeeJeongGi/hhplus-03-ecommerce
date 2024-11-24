package com.hhplus.e_commerce.infra.impl

import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class KafkaMessageConsumer {

    @KafkaListener(topics = ["payment-events"], groupId = "payment-group")
    fun consumeMessage(payload: String) {
        // 수신된 메시지를 처리하는 로직
        println("Kafka 메시지 수신 완료: $payload")
        // 여기에서 수신된 메시지를 비즈니스 로직에 맞게 처리
    }
}