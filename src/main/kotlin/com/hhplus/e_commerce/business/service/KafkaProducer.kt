package com.hhplus.e_commerce.business.service

import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class KafkaProducer(
    private val kafkaTemplate: KafkaTemplate<String, String>,
) {

    fun sendMessage(topic: String, message: String) {
        kafkaTemplate.send(topic, message)
    }
}