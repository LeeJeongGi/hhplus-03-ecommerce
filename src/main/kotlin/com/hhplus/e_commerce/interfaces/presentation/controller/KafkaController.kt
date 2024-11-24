package com.hhplus.e_commerce.interfaces.presentation.controller

import com.hhplus.e_commerce.business.service.KafkaProducer
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/kafka")
class KafkaController(
    private val kafkaProducer: KafkaProducer
) {

    @GetMapping("/send")
    fun sendMessage(@RequestParam message: String): String {
        kafkaProducer.sendMessage("example-topic", message)
        return "Message sent: $message"
    }
}