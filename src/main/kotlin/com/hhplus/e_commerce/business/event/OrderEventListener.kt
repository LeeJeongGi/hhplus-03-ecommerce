package com.hhplus.e_commerce.business.event

import com.hhplus.e_commerce.business.dto.PaymentSaveResultDto
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class OrderEventListener {

    @Async
    @EventListener
    fun listen(event: OrderCompletedEvent) {
        logOrderCompletion(event.paymentSaveResultDto)
    }

    private fun logOrderCompletion(paymentSaveResultDto: PaymentSaveResultDto) {
        println("주문 완료 이벤트 수신: " +
                "[userId = ${paymentSaveResultDto.userId}, " +
                "orderId = ${paymentSaveResultDto.orderId}, " +
                "결제 일자 = ${paymentSaveResultDto.paymentDate}]")
    }
}