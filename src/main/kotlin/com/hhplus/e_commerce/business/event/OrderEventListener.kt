package com.hhplus.e_commerce.business.event

import com.hhplus.e_commerce.business.dto.PaymentSaveResultDto
import com.hhplus.e_commerce.common.error.code.ErrorCode
import com.hhplus.e_commerce.common.error.exception.BusinessException
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class OrderEventListener {

    @Async
    @EventListener
    fun listen(event: OrderCompletedEvent) {
        try {
            logOrderCompletion(event.paymentSaveResultDto)
        } catch (e: Exception) {
            println("주문 완료 이벤트 처리 중 오류 발생: ${e.message}")
        }
    }

    private fun logOrderCompletion(paymentSaveResultDto: PaymentSaveResultDto) {

        /**
         * 외부 API 연동시 실패했을 때 결제 정보 정상적으로 저장되는지 확인하기 위한 예외 처리.
         * 단일 테스트 실행시 orderId 1L 이기 때문에 실패해도 정상적으로 저장되는지 검증 확인
         * 전체 테스트 실행시 orderId는 1L 아니기 때문에 성공해도 정상적으로 저장되는지 검증 확인
         */
        if (paymentSaveResultDto.orderId == 1L) {
            throw BusinessException.BadRequest(ErrorCode.Common.BAD_REQUEST)
        }

        println("주문 완료 이벤트 수신: " +
                "[userId = ${paymentSaveResultDto.userId}, " +
                "orderId = ${paymentSaveResultDto.orderId}, " +
                "결제 일자 = ${paymentSaveResultDto.paymentDate}]")
    }
}