package com.hhplus.e_commerce.business.facade

import com.hhplus.e_commerce.business.dto.PaymentSaveDto
import com.hhplus.e_commerce.business.dto.PaymentSaveResultDto
import com.hhplus.e_commerce.business.event.OrderEventPublisher
import com.hhplus.e_commerce.business.service.BalanceService
import com.hhplus.e_commerce.business.service.OrderService
import com.hhplus.e_commerce.business.service.PaymentService
import com.hhplus.e_commerce.common.error.code.ErrorCode
import com.hhplus.e_commerce.common.error.exception.BusinessException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PaymentFacade(
    private val orderService: OrderService,
    private val balanceService: BalanceService,
    private val paymentService: PaymentService,
    private val orderEventPublisher: OrderEventPublisher
) {

    @Transactional
    fun processPayment(orderId: Long): PaymentSaveResultDto {

        // 1. 주문 정보 조회
        val order = orderService.findOrderById(orderId)

        // 2. 주문이 유효한지 확인
        if (order.status != "ORDERED") {
            throw BusinessException.BadRequest(ErrorCode.Order.INVALID_ORDER_STATUS)
        }

        // 3. 유저 잔액 확인
        val userBalance = balanceService.getUserBalance(order.userId)
        if (userBalance.currentAmount < order.totalAmount) {
            throw BusinessException.BadRequest(ErrorCode.User.INSUFFICIENT_BALANCE)
        }

        // 4. 잔액 차감
        val paymentUserBalance = balanceService.changeBalance(order.userId, order.totalAmount)

        val paymentSaveDto = PaymentSaveDto(
            userId = order.userId,
            orderId = order.orderId,
            status = "COMPLETED",
            amount = order.totalAmount,
        )
        // 5. 결제 정보 저장
        val result = paymentService.save(paymentSaveDto)

        // 6. 주문 상태 업데이트
        orderService.updateOrderStatus(order.orderId, "PAID")

        // 7. 데이터 플랫폼 이벤트 발행
        orderEventPublisher.publishDataPlatformEvent(result)

        return result
    }
}